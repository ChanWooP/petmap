package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.config.redis.MyRedis;
import com.cwpark.petmap.petmap.data.classid.UserOrderClassId;
import com.cwpark.petmap.petmap.data.domain.*;
import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.dto.StockDto;
import com.cwpark.petmap.petmap.data.dto.UserOrderDto;
import com.cwpark.petmap.petmap.data.persistence.StockRepository;
import com.cwpark.petmap.petmap.data.persistence.UserOrderRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class UserOrderDao {
    private final UserOrderRepository userOrderRepository;

    @Autowired
    public UserOrderDao(UserOrderRepository userOrderRepository) {
        this.userOrderRepository = userOrderRepository;
    }

    public Long getUserOrderId(String user) {
        return userOrderRepository.getUserOrderId(user);
    }

    public void saveUserOrder(UserOrder userOrder) {
        userOrderRepository.save(userOrder);
    }

    public void deleteUserOrder(UserOrder userOrder) {
        userOrderRepository.delete(userOrder);
    }

    public UserOrderDto getOneUserOrder(String user, String ordDt, Long ordId) {
        UserOrderClassId userOrderClassId = UserOrderClassId.builder()
                .user(user)
                .ordDt(ordDt)
                .ordId(ordId)
                .build();

        Optional<UserOrder> optional = userOrderRepository.findById(userOrderClassId);
        UserOrder userOrder = optional.orElse(null);

        if(userOrder != null) {
            return UserOrderDto.builder()
                    .user(userOrder.getUser().getUserId())
                    .ordDt(userOrder.getOrdDt())
                    .ordId(userOrder.getOrdId())
                    .ordAddress(userOrder.getOrdAddress())
                    .ordName(userOrder.getOrdName())
                    .ordPhone(userOrder.getOrdPhone())
                    .ordTotAmt(userOrder.getOrdTotAmt())
                    .ordCouDc(userOrder.getOrdCouDc())
                    .ordPoiDc(userOrder.getOrdPoiDc())
                    .ordDelAmt(userOrder.getOrdDelAmt())
                    .ordNetAmt(userOrder.getOrdNetAmt())
                    .build();
        } else {
            return null;
        }
    }

    public Page<UserOrderDto> getListUserOrder(String user, String frDt, String toDt, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "regDate"));
        Page<UserOrder> result = userOrderRepository.findByUserAndOrdDtBetween(User.builder().userId(user).build(), frDt, toDt, pageable);

        return result.map(u -> UserOrderDto.builder()
                .user(u.getUser().getUserId())
                .ordDt(u.getOrdDt())
                .ordId(u.getOrdId())
                .ordAddress(u.getOrdAddress())
                .ordName(u.getOrdName())
                .ordPhone(u.getOrdPhone())
                .ordTotAmt(u.getOrdTotAmt())
                .ordCouDc(u.getOrdCouDc())
                .ordPoiDc(u.getOrdPoiDc())
                .ordDelAmt(u.getOrdDelAmt())
                .ordNetAmt(u.getOrdNetAmt())
                .build());
    }

}
