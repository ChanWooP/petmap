package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.SelOrderDao;
import com.cwpark.petmap.petmap.data.dao.UserCartDao;
import com.cwpark.petmap.petmap.data.domain.*;
import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.dto.SelOrderDto;
import com.cwpark.petmap.petmap.data.dto.UserCartDto;
import com.cwpark.petmap.petmap.data.dto.UserOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserCartService {
    private final UserCartDao userCartDao;

    @Autowired
    public UserCartService(UserCartDao userCartDao) {
        this.userCartDao = userCartDao;
    }

    public void postUserCart(UserCartDto userCartDto) throws Exception {
        UserCart userCart = UserCart.builder()
                .user(User.builder().userId(userCartDto.getUser()).build())
                .item(Item.builder().user(User.builder()
                                .userId(userCartDto.getItem().getUser()).build())
                        .selItemId(userCartDto.getItem().getSelItemId())
                        .build())
                .cartCnt(userCartDto.getCartCnt())
                .build();

        if(userCartDao.existsById(userCart)) {
            throw new DataIntegrityViolationException("이미 장바구니에 해당 상품이 들어있습니다");
        } else {
            userCartDao.saveUserCart(userCart);
        }
    }

    public void putUserCart(UserCartDto userCartDto) {
        userCartDao.saveUserCart(UserCart.builder()
                .user(User.builder().userId(userCartDto.getUser()).build())
                .item(Item.builder().user(User.builder()
                                .userId(userCartDto.getItem().getUser()).build())
                        .selItemId(userCartDto.getItem().getSelItemId())
                        .build())
                .cartCnt(userCartDto.getCartCnt())
                .build());
    }

    public void deleteUserCart(UserCartDto userCartDto) {
        userCartDao.deleteUserCart(UserCart.builder()
                .user(User.builder().userId(userCartDto.getUser()).build())
                .item(Item.builder().user(User.builder()
                                .userId(userCartDto.getItem().getUser()).build())
                        .selItemId(userCartDto.getItem().getSelItemId())
                        .build())
                .cartCnt(userCartDto.getCartCnt())
                .build());
    }

    public Map<String, List<ItemDto>> findByUserOrderByItem(String userId) {
        List<UserCartDto> list = userCartDao.findByUserOrderByItem(User.builder().userId(userId).build());
        Map<String, List<ItemDto>> item = new HashMap<>();
        List<ItemDto> tempItem = null;
        String store = "";

        for(UserCartDto u : list) {
            if(!u.getItem().getUser().equals(store)) {
                store = u.getItem().getUser();
                tempItem = new ArrayList<>();
            }

            if(tempItem != null) {
                u.getItem().setSelSaleCount(u.getCartCnt());
                tempItem.add(u.getItem());
                item.put(store, tempItem);
            }
        }

        return item;
    }

}
