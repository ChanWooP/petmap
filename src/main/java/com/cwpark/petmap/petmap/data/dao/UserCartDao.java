package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.config.redis.MyRedis;
import com.cwpark.petmap.petmap.data.classid.ItemClassId;
import com.cwpark.petmap.petmap.data.classid.UserCartClassId;
import com.cwpark.petmap.petmap.data.domain.*;
import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.dto.UserCartDto;
import com.cwpark.petmap.petmap.data.persistence.ItemRepository;
import com.cwpark.petmap.petmap.data.persistence.UserCartRepository;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.cwpark.petmap.petmap.data.domain.QItem.item;

@Repository
public class UserCartDao {
    private final UserCartRepository userCartRepository;

    @Autowired
    public UserCartDao(UserCartRepository userCartRepository) {
        this.userCartRepository = userCartRepository;
    }

    public Boolean existsById(UserCart userCart) {
        UserCartClassId id = UserCartClassId.builder()
                .user(userCart.getUser().getUserId())
                .item(ItemClassId.builder()
                        .user(userCart.getItem().getUser().getUserId())
                        .selItemId(userCart.getItem().getSelItemId())
                        .build())
                .build();

        return userCartRepository.existsById(id);
    }

    public void saveUserCart(UserCart userCart) {
        this.userCartRepository.save(userCart);
    }

    public void deleteUserCart(UserCart userCart) {
        this.userCartRepository.delete(userCart);
    }

    public List<UserCartDto> findByUserOrderByItem(User user) {
        List<UserCart> list = userCartRepository.findByUserOrderByItem(user);

        return list.stream().map(u -> UserCartDto.builder()
                .user(u.getUser().getUserId())
                .item(ItemDto.builder()
                        .user(u.getItem().getUser().getUserId()+"/"+u.getItem().getUser().getUserBizName())
                        .selItemId(u.getItem().getSelItemId())
                        .selItemName(u.getItem().getSelItemName())
                        .selMiniImg(u.getItem().getSelMiniImg())
                        .selItemPrice(u.getItem().getSelItemPrice())
                        .selDeilPrice(u.getItem().getSelDeilPrice())
                        .build())
                .cartCnt(u.getCartCnt())
                .build()).collect(Collectors.toList());
    }

}
