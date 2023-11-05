package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.data.classid.SelOrderClassId;
import com.cwpark.petmap.petmap.data.classid.SelOrderSaleClassId;
import com.cwpark.petmap.petmap.data.domain.Item;
import com.cwpark.petmap.petmap.data.domain.SelItemReview;
import com.cwpark.petmap.petmap.data.domain.SelOrderSale;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.dto.SelItemReviewDto;
import com.cwpark.petmap.petmap.data.dto.SelOrderDto;
import com.cwpark.petmap.petmap.data.dto.SelOrderSaleDto;
import com.cwpark.petmap.petmap.data.persistence.SelItemReviewRepository;
import com.cwpark.petmap.petmap.data.persistence.SelOrderSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SelItemReviewDao {
    private final SelItemReviewRepository selItemReviewRepository;

    @Autowired
    public SelItemReviewDao(SelItemReviewRepository selItemReviewRepository) {
        this.selItemReviewRepository = selItemReviewRepository;
    }

    public Long getSelItemReviewId(String store, String item) {
        return selItemReviewRepository.getUserOrderId(store, item);
    }

    public void saveSelOrderSale(SelItemReview selItemReview) {
        selItemReviewRepository.save(selItemReview);
    }

    public Page<SelItemReviewDto> findByItem(String store, String item, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "regDate"));
        Page<SelItemReview> result = selItemReviewRepository.findByItem(Item.builder()
                .user(User.builder().userId(store).build())
                .selItemId(item)
                .build(), pageable);

        return result.map(s -> SelItemReviewDto.builder()
                .user(s.getUser().getUserId())
                .reviewDt(s.getReviewDt())
                .reviewId(s.getReviewId())
                .reviewStarPoint(s.getReviewStarPoint())
                .reviewText(s.getReviewText())
                .build());
    }

}
