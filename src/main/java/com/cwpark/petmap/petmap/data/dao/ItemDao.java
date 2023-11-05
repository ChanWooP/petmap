package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.config.redis.MyRedis;
import com.cwpark.petmap.petmap.data.classid.ItemClassId;
import com.cwpark.petmap.petmap.data.domain.*;
import com.cwpark.petmap.petmap.data.dto.CategoryDto;
import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.persistence.CategoryRepository;
import com.cwpark.petmap.petmap.data.persistence.ItemRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.cwpark.petmap.petmap.data.domain.QItem.item;

@Repository
public class ItemDao {
    private final ItemRepository itemRepository;

    private final MyRedis myRedis;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public ItemDao(ItemRepository itemRepository, MyRedis myRedis, JPAQueryFactory jpaQueryFactory) {
        this.itemRepository = itemRepository;
        this.myRedis = myRedis;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public void postItem(Item item) {
        ItemClassId id = ItemClassId.builder()
                .user(item.getUser().getUserId())
                .selItemId(item.getSelItemId())
                .build();

        if(itemRepository.existsById(id)) {
            throw new DataIntegrityViolationException("상품 코드가 중복되었습니다");
        }

        itemRepository.save(item);
    }

    public void putItem(Item item) {
        TransactionStatus transactionStatus = myRedis.startDBTransacton();

        ItemClassId id = ItemClassId.builder()
                .user(item.getUser().getUserId())
                .selItemId(item.getSelItemId())
                .build();

        try {
            if(myRedis.tryLock("itemLock", TimeUnit.SECONDS, 60, 10)) {
                Optional<Item> optional = itemRepository.findById(id);
                Item original = null;

                if(optional.isPresent()) {
                    original = optional.get();

                    item.setSelHeartCount(original.getSelHeartCount() + item.getSelHeartCount());
                    item.setSelStarPoint(original.getSelStarPoint() + item.getSelStarPoint());
                    item.setSelSaleCount(original.getSelSaleCount() + item.getSelSaleCount());
                    item.setSelStockCount(original.getSelStockCount() + item.getSelStockCount());

                    item.setSelStarPointCnt(original.getSelStarPointCnt() + item.getSelStarPointCnt());
                    item.setSelStarPointAvg((original.getSelStarPoint() + item.getSelStarPoint()) / (original.getSelStarPointCnt() + item.getSelStarPointCnt()));

                    itemRepository.save(item);
                }

                myRedis.commitDB(transactionStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            myRedis.rollbackDB(transactionStatus);
        } finally {
            if(myRedis.canUnlock("itemLock")) {
                myRedis.unlock("itemLock");
            }
        }

    }

    public void putItemStock(String user, String item, int stockQty) {
        TransactionStatus transactionStatus = myRedis.startDBTransacton();

        ItemClassId id = ItemClassId.builder()
                .user(user)
                .selItemId(item)
                .build();

        try {
            if(myRedis.tryLock("itemLock", TimeUnit.SECONDS, 60, 10)) {
                Optional<Item> optional = itemRepository.findById(id);
                Item original = null;

                if(optional.isPresent()) {
                    original = optional.get();
                } else {
                    throw new EmptyResultDataAccessException(1);
                }

                original.setSelStockCount(original.getSelStockCount() + stockQty);

                itemRepository.save(original);

                myRedis.commitDB(transactionStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            myRedis.rollbackDB(transactionStatus);
        } finally {
            if(myRedis.canUnlock("itemLock")) {
                myRedis.unlock("itemLock");
            }
        }

    }

    public void deleteItem(Item item) {
        itemRepository.delete(item);
    }

    public Page<ItemDto> getItem(String itemSearch, int page, String sort, String user) {
        BooleanBuilder builder = new BooleanBuilder();
        QItem qItem = QItem.item;
        Pageable pageable = PageRequest.of(page,9, Sort.by(Sort.Direction.DESC, sort));

        builder.and(qItem.user.userId.eq(user));
        builder.and(qItem.selItemId.like("%" + itemSearch + "%").or(qItem.selItemName.like("%" + itemSearch + "%")));

        Page<Item> list = itemRepository.findAll(builder, pageable);
        Page<ItemDto> result = null;

        if(list.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        } else {
            result = list.map(i -> ItemDto.builder()
                    .user(i.getUser().getUserId())
                    .selItemId(i.getSelItemId())
                    .selItemName(i.getSelItemName())
                    .category(i.getCategory().getCategoryId())
                    .selMiniImg(i.getSelMiniImg())
                    .selMainImg(i.getSelMainImg())
                    .selExpln(i.getSelExpln())
                    .selItemPrice(i.getSelItemPrice())
                    .selDeilPrice(i.getSelDeilPrice())
                    .selStarPoint(i.getSelStarPoint())
                    .selSaleCount(i.getSelSaleCount())
                    .selHeartCount(i.getSelHeartCount())
                    .selStockCount(i.getSelStockCount())
                    .selStarPointAvg(i.getSelStarPointAvg())
                    .build());
        }

        return result;
    }

    public Page<ItemDto> getItemSearch(String type, String search, int page, String sort) {
        BooleanBuilder builder = new BooleanBuilder();
        QItem qItem = QItem.item;
        Sort sorts = null;

        if (sort.equals("sale")) {
            sorts = Sort.by(Sort.Direction.DESC, "selSaleCount");
        } else if (sort.equals("star")) {
            sorts = Sort.by(Sort.Direction.DESC, "selStarPointAvg");
        } else if (sort.equals("dprice")) {
            sorts = Sort.by(Sort.Direction.ASC, "selItemPrice");
        } else if (sort.equals("price")) {
            sorts = Sort.by(Sort.Direction.DESC, "selItemPrice");
        } else {
            sorts = Sort.by(Sort.Direction.DESC, "regDate");
        }

        Pageable pageable = PageRequest.of(page,20, sorts);

        if(type.equals("item")) {
            builder.and(qItem.selItemId.like("%" + search + "%"));
            builder.or(qItem.selItemName.like("%" + search + "%"));
        } else if(type.equals("category")) {
            builder.and(qItem.category.eq(Category.builder().categoryId(Long.valueOf(search)).build()));
        }


        Page<Item> list = itemRepository.findAll(builder, pageable);
        Page<ItemDto> result = null;

        if(list.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        } else {
            result = list.map(i -> ItemDto.builder()
                    .user(i.getUser().getUserId())
                    .selItemId(i.getSelItemId())
                    .selItemName(i.getSelItemName())
                    .category(i.getCategory().getCategoryId())
                    .selMiniImg(i.getSelMiniImg())
                    .selMainImg(i.getSelMainImg())
                    .selExpln(i.getSelExpln())
                    .selItemPrice(i.getSelItemPrice())
                    .selDeilPrice(i.getSelDeilPrice())
                    .selStarPoint(i.getSelStarPoint())
                    .selSaleCount(i.getSelSaleCount())
                    .selHeartCount(i.getSelHeartCount())
                    .selStockCount(i.getSelStockCount())
                    .selStarPointAvg(i.getSelStarPointAvg())
                    .build());
        }

        return result;
    }

    public List<ItemDto> getItemAll(String user) {
        List<Item> list = itemRepository.findByUser(User.builder().userId(user).build());
        List<ItemDto> result = null;

        if(list.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        } else {
            result = list.stream().map(i -> ItemDto.builder()
                    .user(i.getUser().getUserId())
                    .selItemId(i.getSelItemId())
                    .selItemName(i.getSelItemName())
                    .category(i.getCategory().getCategoryId())
                    .selMiniImg(i.getSelMiniImg())
                    .selMainImg(i.getSelMainImg())
                    .selExpln(i.getSelExpln())
                    .selItemPrice(i.getSelItemPrice())
                    .selDeilPrice(i.getSelDeilPrice())
                    .selStarPoint(i.getSelStarPoint())
                    .selSaleCount(i.getSelSaleCount())
                    .selHeartCount(i.getSelHeartCount())
                    .selStockCount(i.getSelStockCount())
                    .build()).collect(Collectors.toList());;
        }

        return result;
    }

    public ItemDto getItemOne(String user, String selItemId) {
        ItemClassId id = ItemClassId.builder()
                .user(user)
                .selItemId(selItemId)
                .build();

        Optional<Item> optional = itemRepository.findById(id);
        Item i = null;

        if(optional.isPresent()) {
            i = optional.get();
        } else {
            throw new EmptyResultDataAccessException(1);
        }

        return ItemDto.builder()
                .user(i.getUser().getUserId())
                .selItemId(i.getSelItemId())
                .selItemName(i.getSelItemName())
                .category(i.getCategory().getCategoryId())
                .selMiniImg(i.getSelMiniImg())
                .selMainImg(i.getSelMainImg())
                .selExpln(i.getSelExpln())
                .selItemPrice(i.getSelItemPrice())
                .selDeilPrice(i.getSelDeilPrice())
                .selStarPoint(i.getSelStarPoint())
                .selSaleCount(i.getSelSaleCount())
                .selHeartCount(i.getSelHeartCount())
                .selStockCount(i.getSelStockCount())
                .selStarPointAvg(i.getSelStarPointAvg())
                .build();
    }

    public Map<String, List<ItemDto>> getItemList(List<String> list, Map<String, Map<String, List<String>>> map) {
        Map<String, List<ItemDto>> rtnMap = new HashMap<>();

        for(String str : list) {
            List<Item> itemList = jpaQueryFactory
                    .select(item)
                    .from(item)
                    .where(item.user.eq(User.builder().userId(str).build())
                          ,item.selItemId.in(map.get(str).get("item")))
                    .fetch();

            for(Item i : itemList) {
                int index = map.get(str).get("item").indexOf(i.getSelItemId());

                i.setSelSaleCount(Integer.parseInt(map.get(str).get("cnt").get(index)));
            }

            List<ItemDto> itemDtoList = itemList.stream().map(i -> ItemDto.builder()
                    .user(i.getUser().getUserId() + "/" + i.getUser().getUserBizName())
                    .selItemId(i.getSelItemId())
                    .selItemName(i.getSelItemName())
                    .selItemPrice(i.getSelItemPrice())
                    .selSaleCount(i.getSelSaleCount())
                    .selMiniImg(i.getSelMiniImg())
                    .selDeilPrice(i.getSelDeilPrice())
                    .build()).collect(Collectors.toList());

            str = itemDtoList.get(0).getUser();

            rtnMap.put(str, itemDtoList);
        }

        return rtnMap;
    }


}
