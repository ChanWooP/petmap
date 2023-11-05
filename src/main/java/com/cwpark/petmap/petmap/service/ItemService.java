package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.classid.ItemClassId;
import com.cwpark.petmap.petmap.data.dao.CategoryDao;
import com.cwpark.petmap.petmap.data.dao.ItemDao;
import com.cwpark.petmap.petmap.data.domain.Category;
import com.cwpark.petmap.petmap.data.domain.Item;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.dto.CategoryDto;
import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.dto.StockDto;
import com.cwpark.petmap.petmap.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Service
public class ItemService {
    private final ItemDao itemDao;

    @Autowired
    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public void postItem(ItemDto itemDto, List<MultipartFile> files) throws IOException {
        String saveFileNameMini = itemDto.getSelMiniImg();
        String saveFileNameMain = itemDto.getSelMainImg();

        if(files != null) {
            for (MultipartFile m : files) {
                if (m.getOriginalFilename().equals("mini")) {
                    saveFileNameMini = FileUtils.singleFileUpload(m, "item");
                } else {
                    saveFileNameMain = FileUtils.singleFileUpload(m, "item");
                }
            }
        }

        itemDao.postItem(Item.builder()
                        .user(User.builder().userId(itemDto.getUser()).build())
                        .selItemId(itemDto.getSelItemId())
                        .selItemName(itemDto.getSelItemName())
                        .category(Category.builder().categoryId(itemDto.getCategory()).build())
                        .selMiniImg(saveFileNameMini)
                        .selMainImg(saveFileNameMain)
                        .selExpln(itemDto.getSelExpln())
                        .selItemPrice(itemDto.getSelItemPrice())
                        .selDeilPrice(itemDto.getSelDeilPrice())
                .build());
    }

    public void putItem(ItemDto itemDto, List<MultipartFile> files) throws IOException {
        String saveFileNameMini = itemDto.getSelMiniImg();
        String saveFileNameMain = itemDto.getSelMainImg();

        if(files != null) {
            for (MultipartFile m : files) {
                if (m.getOriginalFilename().equals("mini")) {
                    saveFileNameMini = FileUtils.singleFileUpload(m, "item");
                } else {
                    saveFileNameMain = FileUtils.singleFileUpload(m, "item");
                }
            }
        }

        itemDao.putItem(Item.builder()
                .user(User.builder().userId(itemDto.getUser()).build())
                .selItemId(itemDto.getSelItemId())
                .selItemName(itemDto.getSelItemName())
                .category(Category.builder().categoryId(itemDto.getCategory()).build())
                .selMiniImg(saveFileNameMini)
                .selMainImg(saveFileNameMain)
                .selExpln(itemDto.getSelExpln())
                .selItemPrice(itemDto.getSelItemPrice())
                .selDeilPrice(itemDto.getSelDeilPrice())
                        .selStockCount(itemDto.getSelStockCount())
                        .selStarPoint(itemDto.getSelStarPoint())
                        .selHeartCount(itemDto.getSelHeartCount())
                        .selSaleCount(itemDto.getSelSaleCount())
                        .selStarPointCnt(itemDto.getSelStarPointCnt())
                .build());
    }


    public void deleteItem(ItemDto itemDto) {
        itemDao.deleteItem(Item.builder()
                .user(User.builder().userId(itemDto.getUser()).build())
                .selItemId(itemDto.getSelItemId())
                .build());
    }

    public Page<ItemDto> getItem(String itemSearch, int page, String sort, String user) {
        itemSearch = itemSearch.equals("all") ? "" : itemSearch;

        return itemDao.getItem(itemSearch, page, sort, user);
    }

    public Page<ItemDto> getItemSearch(String type, String search, int page, String sort) {
        search = search.equals("all") ? "" : search;

        return itemDao.getItemSearch(type, search, page, sort);
    }

    public List<ItemDto> getItemAll(String user) {
        return itemDao.getItemAll(user);
    }

    public ItemDto getItemOne(String user, String selItemId) {
        return itemDao.getItemOne(user, selItemId);
    }

    public void putItemStock(StockDto stockDto) {
        itemDao.putItemStock(stockDto.getItemDto().getUser(), stockDto.getItemDto().getSelItemId(), stockDto.getStockQty());
    }

    public Map<String, List<ItemDto>> getItemList(String[] storeArray, String[] itemArray, String[] cntArray) {
        List<String> storeList = new ArrayList<>();
        Map<String, Map<String, List<String>>> itemMap = new HashMap<>();
        Map<String, List<ItemDto>> result = null;

        for (int i=0; i<storeArray.length; i++) {
            Map<String, List<String>> map = null;
            List<String> tempItem = null;
            List<String> tempCnt = null;

            if (!storeList.contains(storeArray[i])) {
                storeList.add(storeArray[i]);
            }

            if(itemMap.get(storeArray[i]) == null) {
                map = new HashMap<>();
                tempItem = new ArrayList<>();
                tempCnt = new ArrayList<>();
            } else {
                map = itemMap.get(storeArray[i]);
                tempItem = map.get("item");
                tempCnt = map.get("cnt");
            }

            tempItem.add(itemArray[i]);
            tempCnt.add(cntArray[i]);

            map.put("item", tempItem);
            map.put("cnt", tempCnt);

            itemMap.put(storeArray[i], map);
        }

        result = itemDao.getItemList(storeList, itemMap);

        return result;
    }
}
