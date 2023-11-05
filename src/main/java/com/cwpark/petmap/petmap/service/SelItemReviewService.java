package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.ItemDao;
import com.cwpark.petmap.petmap.data.dao.SelItemReviewDao;
import com.cwpark.petmap.petmap.data.dao.SelOrderSaleDao;
import com.cwpark.petmap.petmap.data.domain.*;
import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.dto.SelItemReviewDto;
import com.cwpark.petmap.petmap.data.dto.SelOrderSaleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;


@Service
public class SelItemReviewService {
    private final SelItemReviewDao selItemReviewDao;
    private final ItemDao itemDao;

    @Autowired
    public SelItemReviewService(SelItemReviewDao selItemReviewDao, ItemDao itemDao) {
        this.selItemReviewDao = selItemReviewDao;
        this.itemDao = itemDao;
    }

    public void postSelItemReview(SelItemReviewDto selItemReviewDto) {
        Long id = selItemReviewDao.getSelItemReviewId(selItemReviewDto.getItem().getUser(), selItemReviewDto.getItem().getSelItemId());
        selItemReviewDto.setReviewId(id);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String strToday = sdf.format(c1.getTime());

        selItemReviewDao.saveSelOrderSale(SelItemReview.builder()
                        .item(Item.builder()
                                .user(User.builder().userId(selItemReviewDto.getItem().getUser()).build())
                                .selItemId(selItemReviewDto.getItem().getSelItemId())
                                .build())
                        .user(User.builder().userId(selItemReviewDto.getUser()).build())
                        .reviewDt(strToday)
                        .reviewId(id)
                        .reviewStarPoint(selItemReviewDto.getReviewStarPoint())
                        .reviewText(selItemReviewDto.getReviewText())
                .build());
    }

    public Page<SelItemReviewDto> findByItem(String store, String item, int page) {
        return selItemReviewDao.findByItem(store, item, page);
    }

}
