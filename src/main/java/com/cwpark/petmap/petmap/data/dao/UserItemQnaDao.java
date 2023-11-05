package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.data.classid.ItemClassId;
import com.cwpark.petmap.petmap.data.classid.UserItemQnaClassId;
import com.cwpark.petmap.petmap.data.domain.Coupon;
import com.cwpark.petmap.petmap.data.domain.Item;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserItemQna;
import com.cwpark.petmap.petmap.data.dto.CouponDto;
import com.cwpark.petmap.petmap.data.dto.UserItemQnaDto;
import com.cwpark.petmap.petmap.data.dto.UserQnaDto;
import com.cwpark.petmap.petmap.data.persistence.CouponRepository;
import com.cwpark.petmap.petmap.data.persistence.UserItemQnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserItemQnaDao {
    private final UserItemQnaRepository userItemQnaRepository;

    @Autowired
    public UserItemQnaDao(UserItemQnaRepository userItemQnaRepository) {
        this.userItemQnaRepository = userItemQnaRepository;
    }

    public void postUserItemQna(UserItemQna userItemQna) {
        Long id = userItemQnaRepository.getUserItemQnaId(userItemQna.getUser().getUserId(),
                userItemQna.getItem().getUser().getUserId(),
                userItemQna.getItem().getSelItemId());

        userItemQna.setItemQnaId(id);

        userItemQnaRepository.save(userItemQna);
    }

    public void putUserItemQna(UserItemQna userItemQna) {
        userItemQnaRepository.save(userItemQna);
    }

    public void deleteUserItemQna(UserItemQna userItemQna) {
        userItemQnaRepository.delete(userItemQna);
    }

    public Page<UserItemQnaDto> getUserItemQna(String store, String item, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "regDate"));
        Page<UserItemQna> list = userItemQnaRepository.findByItem(Item.builder()
                        .user(User.builder().userId(store).build())
                        .selItemId(item).build(), pageable);

        Page<UserItemQnaDto> rtnList = null;

        if(!list.isEmpty()) {
            rtnList = list.map(u -> UserItemQnaDto.builder()
                    .store(u.getItem().getUser().getUserId())
                    .item(u.getItem().getSelItemId())
                    .user(u.getUser().getUserId())
                    .itemQnaId(u.getItemQnaId())
                    .itemQnaQuestion(u.getItemQnaQuestion())
                    .itemQnaAnswer(u.getItemQnaAnswer())
                    .itemQnaAnswerYn(u.getItemQnaAnswerYn())
                    .build());
        }

        return rtnList;
    }
}
