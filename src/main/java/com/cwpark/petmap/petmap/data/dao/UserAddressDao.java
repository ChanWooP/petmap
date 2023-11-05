package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.config.redis.MyRedis;
import com.cwpark.petmap.petmap.data.classid.ItemClassId;
import com.cwpark.petmap.petmap.data.domain.*;
import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.dto.UserAddressDto;
import com.cwpark.petmap.petmap.data.persistence.ItemRepository;
import com.cwpark.petmap.petmap.data.persistence.UserAddressRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
public class UserAddressDao {
    private final UserAddressRepository userAddressRepository;

    @Autowired
    public UserAddressDao(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

   public void postUserAddress(UserAddress userAddress) {
        userAddressRepository.save(userAddress);
   }

   public void deleteUserAddress(UserAddress userAddress) {
        userAddressRepository.delete(userAddress);
   }

   public Long getUserAddressCnt(User user) {
        return userAddressRepository.countByUser(user);
   }

   public List<UserAddressDto> getUserAddress(User user) {
        List<UserAddress> list = userAddressRepository.findByUser(user);
        List<UserAddressDto> result = null;

        result = list.stream().map(u -> UserAddressDto.builder()
                .addressId(u.getAddressId())
                .user(u.getUser().getUserId())
                .addressUserPhone(u.getAddressUserPhone())
                .addressUserZipcode(u.getAddressUserZipcode())
                .addressUserAddress1(u.getAddressUserAddress1())
                .addressUserAddress2(u.getAddressUserAddress2())
                .addressUserName(u.getAddressUserName())
                .build()).collect(Collectors.toList());

        return result;
   }

}
