package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.data.classid.ItemClassId;
import com.cwpark.petmap.petmap.data.classid.SelOrderClassId;
import com.cwpark.petmap.petmap.data.classid.SelOrderItemClassId;
import com.cwpark.petmap.petmap.data.domain.SelOrder;
import com.cwpark.petmap.petmap.data.domain.SelOrderItem;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.dto.*;
import com.cwpark.petmap.petmap.data.persistence.SelOrderItemRepository;
import com.cwpark.petmap.petmap.data.persistence.SelOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SelOrderItemDao {
    private final SelOrderItemRepository selOrderItemRepository;

    @Autowired
    public SelOrderItemDao(SelOrderItemRepository selOrderItemRepository) {
        this.selOrderItemRepository = selOrderItemRepository;
    }

    public void saveSelOrderItem(SelOrderItem selOrderItem) {
        selOrderItemRepository.save(selOrderItem);
    }

    public void deleteSelOrderItem(SelOrderItem selOrderItem) {
        selOrderItemRepository.delete(selOrderItem);
    }

    public SelOrderItemDto getOneSelOrderItem(String user, String ordDt, Long ordId, String store, String itemId) {
        SelOrderClassId selOrderClassId = SelOrderClassId.builder()
                .user(user)
                .ordDt(ordDt)
                .ordId(ordId)
                .build();

        ItemClassId itemClassId = ItemClassId.builder()
                .user(store)
                .selItemId(itemId)
                .build();

        SelOrderItemClassId selOrderItemClassId = SelOrderItemClassId.builder()
                .selOrder(selOrderClassId)
                .item(itemClassId)
                .build();

        Optional<SelOrderItem> optional = selOrderItemRepository.findById(selOrderItemClassId);
        SelOrderItem selOrderItem = optional.orElse(null);

        if(selOrderItem != null) {
            return SelOrderItemDto.builder()
                    .selOrderDto(SelOrderDto.builder()
                            .user(selOrderItem.getSelOrder().getUser().getUserId())
                            .ordDt(selOrderItem.getSelOrder().getOrdDt())
                            .ordId(selOrderItem.getSelOrder().getOrdId())
                            .userOrderDto(UserOrderDto.builder()
                                    .user(selOrderItem.getSelOrder().getUserOrder().getUser().getUserId())
                                    .ordDt(selOrderItem.getSelOrder().getUserOrder().getOrdDt())
                                    .ordId(selOrderItem.getSelOrder().getUserOrder().getOrdId())
                                    .ordAddress(selOrderItem.getSelOrder().getUserOrder().getOrdAddress())
                                    .ordName(selOrderItem.getSelOrder().getUserOrder().getOrdName())
                                    .ordPhone(selOrderItem.getSelOrder().getUserOrder().getOrdPhone())
                                    .ordTotAmt(selOrderItem.getSelOrder().getUserOrder().getOrdTotAmt())
                                    .ordCouDc(selOrderItem.getSelOrder().getUserOrder().getOrdCouDc())
                                    .ordPoiDc(selOrderItem.getSelOrder().getUserOrder().getOrdPoiDc())
                                    .ordDelAmt(selOrderItem.getSelOrder().getUserOrder().getOrdDelAmt())
                                    .ordNetAmt(selOrderItem.getSelOrder().getUserOrder().getOrdNetAmt())
                                    .build())
                            .ordStatus(selOrderItem.getSelOrder().getOrdStatus())
                            .ordInvoice(selOrderItem.getSelOrder().getOrdInvoice())
                            .ordTotAmt(selOrderItem.getSelOrder().getOrdTotAmt())
                            .ordDelAmt(selOrderItem.getSelOrder().getOrdDelAmt())
                            .build())
                    .itemDto(ItemDto.builder()
                            .user(selOrderItem.getItem().getUser().getUserId())
                            .selItemId(selOrderItem.getItem().getSelItemId())
                            .selItemName(selOrderItem.getItem().getSelItemName())
                            .category(selOrderItem.getItem().getCategory().getCategoryId())
                            .selMiniImg(selOrderItem.getItem().getSelMiniImg())
                            .selMainImg(selOrderItem.getItem().getSelMainImg())
                            .selExpln(selOrderItem.getItem().getSelExpln())
                            .selItemPrice(selOrderItem.getItem().getSelItemPrice())
                            .selDeilPrice(selOrderItem.getItem().getSelDeilPrice())
                            .selStarPoint(selOrderItem.getItem().getSelStarPoint())
                            .selSaleCount(selOrderItem.getItem().getSelSaleCount())
                            .selHeartCount(selOrderItem.getItem().getSelHeartCount())
                            .selStockCount(selOrderItem.getItem().getSelStockCount())
                            .build())
                    .ordCnt(selOrderItem.getOrdCnt())
                    .ordAmt(selOrderItem.getOrdAmt())
                    .itemReviewYn(selOrderItem.getItemReviewYn())
                    .build();
        } else {
            return null;
        }
    }

    public Page<SelOrderItemDto> getListSelOrderItem(String user, String frDt, String toDt, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "regDate"));
        Page<SelOrderItem> result = selOrderItemRepository.getListSelOrderItem(User.builder().userId(user).build(), frDt, toDt, pageable);

        return result.map(s -> SelOrderItemDto.builder()
                .selOrderDto(SelOrderDto.builder()
                        .user(s.getSelOrder().getUser().getUserId())
                        .ordDt(s.getSelOrder().getOrdDt())
                        .ordId(s.getSelOrder().getOrdId())
                        .ordStatus(s.getSelOrder().getOrdStatus())
                        .ordInvoice(s.getSelOrder().getOrdInvoice())
                        .ordTotAmt(s.getSelOrder().getOrdTotAmt())
                        .ordDelAmt(s.getSelOrder().getOrdDelAmt())
                        .build())
                .itemDto(ItemDto.builder()
                        .user(s.getItem().getUser().getUserId())
                        .selItemId(s.getItem().getSelItemId())
                        .selItemName(s.getItem().getSelItemName())
                        .build())
                .ordCnt(s.getOrdCnt())
                .ordAmt(s.getOrdAmt())
                .itemReviewYn(s.getItemReviewYn())
                .build());
    }

    public List<SelOrderItemDto> findBySelOrder(String user, String ordDt, Long ordId) {
        SelOrder selOrder = SelOrder.builder()
                .user(User.builder().userId(user).build())
                .ordDt(ordDt)
                .ordId(ordId)
                .build();

        List<SelOrderItem> list = selOrderItemRepository.findBySelOrder(selOrder);

        return list.stream().map(s -> SelOrderItemDto.builder()
                .selOrderDto(SelOrderDto.builder()
                        .user(s.getSelOrder().getUser().getUserId())
                        .ordDt(s.getSelOrder().getOrdDt())
                        .ordId(s.getSelOrder().getOrdId())
                        .ordStatus(s.getSelOrder().getOrdStatus())
                        .ordInvoice(s.getSelOrder().getOrdInvoice())
                        .ordTotAmt(s.getSelOrder().getOrdTotAmt())
                        .ordDelAmt(s.getSelOrder().getOrdDelAmt())
                        .build())
                .itemDto(ItemDto.builder()
                        .user(s.getItem().getUser().getUserId())
                        .selItemId(s.getItem().getSelItemId())
                        .selItemName(s.getItem().getSelItemName())
                        .category(s.getItem().getCategory().getCategoryId())
                        .selMiniImg(s.getItem().getSelMiniImg())
                        .selMainImg(s.getItem().getSelMainImg())
                        .selExpln(s.getItem().getSelExpln())
                        .selItemPrice(s.getItem().getSelItemPrice())
                        .selDeilPrice(s.getItem().getSelDeilPrice())
                        .selStarPoint(s.getItem().getSelStarPoint())
                        .selSaleCount(s.getItem().getSelSaleCount())
                        .selHeartCount(s.getItem().getSelHeartCount())
                        .selStockCount(s.getItem().getSelStockCount())
                        .build())
                .ordCnt(s.getOrdCnt())
                .ordAmt(s.getOrdAmt())
                .itemReviewYn(s.getItemReviewYn())
                .build()).collect(Collectors.toList());
    }

    public Page<SelOrderItemGroupDto> findByItemGroup(String store, String frDt, String toDt, int page) {
        Pageable pageable = PageRequest.of(page, 30, Sort.by(Sort.Direction.DESC, "itemId"));

        return selOrderItemRepository.findByItemGroup(store, frDt, toDt, pageable).map(s-> SelOrderItemGroupDto.builder()
                .itemId(s.getItemId())
                .itemName(s.getItemName())
                .ordCnt(s.getOrdCnt())
                .ordAmt(s.getOrdAmt())
                .build());
    }

}
