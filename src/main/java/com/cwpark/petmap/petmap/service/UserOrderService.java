package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.StockDao;
import com.cwpark.petmap.petmap.data.dao.UserOrderDao;
import com.cwpark.petmap.petmap.data.domain.Item;
import com.cwpark.petmap.petmap.data.domain.Stock;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserOrder;
import com.cwpark.petmap.petmap.data.dto.StockDto;
import com.cwpark.petmap.petmap.data.dto.UserOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@Service
public class UserOrderService {
    private final UserOrderDao userOrderDao;

    @Autowired
    public UserOrderService(UserOrderDao userOrderDao) {
        this.userOrderDao = userOrderDao;
    }

    public void postUserOrder(UserOrderDto userOrderDto) {
        Long id = userOrderDao.getUserOrderId(userOrderDto.getUser());
        userOrderDto.setOrdId(id);

        userOrderDao.saveUserOrder(UserOrder.builder()
                .user(User.builder().userId(userOrderDto.getUser()).build())
                .ordDt(userOrderDto.getOrdDt())
                .ordId(userOrderDto.getOrdId())
                .ordAddress(userOrderDto.getOrdAddress())
                .ordName(userOrderDto.getOrdName())
                .ordPhone(userOrderDto.getOrdPhone())
                .ordTotAmt(userOrderDto.getOrdTotAmt())
                .ordCouDc(userOrderDto.getOrdCouDc())
                .ordPoiDc(userOrderDto.getOrdPoiDc())
                .ordDelAmt(userOrderDto.getOrdDelAmt())
                .ordNetAmt(userOrderDto.getOrdNetAmt())
                .build());
    }

    public void putUserOrder(UserOrderDto userOrderDto) {
        userOrderDao.saveUserOrder(UserOrder.builder()
                .user(User.builder().userId(userOrderDto.getUser()).build())
                .ordDt(userOrderDto.getOrdDt())
                .ordId(userOrderDto.getOrdId())
                .ordAddress(userOrderDto.getOrdAddress())
                .ordName(userOrderDto.getOrdName())
                .ordPhone(userOrderDto.getOrdPhone())
                .ordTotAmt(userOrderDto.getOrdTotAmt())
                .ordCouDc(userOrderDto.getOrdCouDc())
                .ordPoiDc(userOrderDto.getOrdPoiDc())
                .ordDelAmt(userOrderDto.getOrdDelAmt())
                .ordNetAmt(userOrderDto.getOrdNetAmt())
                .build());
    }

    public void deleteUserOrder(UserOrderDto userOrderDto) {
        userOrderDao.deleteUserOrder(UserOrder.builder()
                .user(User.builder().userId(userOrderDto.getUser()).build())
                .ordDt(userOrderDto.getOrdDt())
                .ordId(userOrderDto.getOrdId())
                .build());
    }

    public UserOrderDto getOneUserOrder(String user, String ordDt, Long ordId) {
        return userOrderDao.getOneUserOrder(user, ordDt, ordId);
    }

    public Page<UserOrderDto> getListUserOrder(String user, String frDt, String toDt, int page) {
        return userOrderDao.getListUserOrder(user, frDt, toDt, page);
    }

}
