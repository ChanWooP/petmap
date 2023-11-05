package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.ItemDao;
import com.cwpark.petmap.petmap.data.dao.UserItemQnaDao;
import com.cwpark.petmap.petmap.data.domain.Category;
import com.cwpark.petmap.petmap.data.domain.Item;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserItemQna;
import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.dto.StockDto;
import com.cwpark.petmap.petmap.data.dto.UserItemQnaDto;
import com.cwpark.petmap.petmap.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class UseritemQnaService {
    private final UserItemQnaDao userItemQnaDao;

    @Autowired
    public UseritemQnaService(UserItemQnaDao userItemQnaDao) {
        this.userItemQnaDao = userItemQnaDao;
    }

    public void postUserItemQna(UserItemQnaDto userItemQnaDto) {
        userItemQnaDao.postUserItemQna(UserItemQna.builder()
                        .item(Item.builder()
                                .user(User.builder().userId(userItemQnaDto.getStore()).build())
                                .selItemId(userItemQnaDto.getItem())
                                .build())
                        .user(User.builder().userId(userItemQnaDto.getUser()).build())
                        .itemQnaId(userItemQnaDto.getItemQnaId())
                        .itemQnaQuestion(userItemQnaDto.getItemQnaQuestion())
                        .itemQnaAnswer(userItemQnaDto.getItemQnaAnswer())
                        .itemQnaAnswerYn(userItemQnaDto.getItemQnaAnswerYn())
                .build());
    }

    public void putUserItemQna(UserItemQnaDto userItemQnaDto) {
        userItemQnaDao.putUserItemQna(UserItemQna.builder()
                .item(Item.builder()
                        .user(User.builder().userId(userItemQnaDto.getStore()).build())
                        .selItemId(userItemQnaDto.getItem())
                        .build())
                .user(User.builder().userId(userItemQnaDto.getUser()).build())
                .itemQnaId(userItemQnaDto.getItemQnaId())
                .itemQnaQuestion(userItemQnaDto.getItemQnaQuestion())
                .itemQnaAnswer(userItemQnaDto.getItemQnaAnswer())
                .itemQnaAnswerYn(userItemQnaDto.getItemQnaAnswerYn())
                .build());
    }


    public void deleteUserItemQna(UserItemQnaDto userItemQnaDto) {
        userItemQnaDao.deleteUserItemQna(UserItemQna.builder()
                .item(Item.builder()
                        .user(User.builder().userId(userItemQnaDto.getStore()).build())
                        .selItemId(userItemQnaDto.getItem())
                        .build())
                .user(User.builder().userId(userItemQnaDto.getUser()).build())
                .itemQnaId(userItemQnaDto.getItemQnaId())
                .itemQnaQuestion(userItemQnaDto.getItemQnaQuestion())
                .itemQnaAnswer(userItemQnaDto.getItemQnaAnswer())
                .itemQnaAnswerYn(userItemQnaDto.getItemQnaAnswerYn())
                .build());
    }

    public Page<UserItemQnaDto> getUserItemQna(String store, String item, int page) {
        return userItemQnaDao.getUserItemQna(store, item, page);
    }
}
