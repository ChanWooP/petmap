package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.data.domain.Coupon;
import com.cwpark.petmap.petmap.data.dto.CouponDto;
import com.cwpark.petmap.petmap.data.persistence.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CouponDao {
    private final CouponRepository couponRepository;

    @Autowired
    public CouponDao(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public void postCoupon(Coupon coupon) {
        if(couponRepository.existsById(coupon.getCouponCode())) {
            throw new DataIntegrityViolationException("쿠폰코드가 중복되었습니다");
        }

        couponRepository.save(coupon);
    }

    public void putCoupon(Coupon coupon) {
        couponRepository.save(coupon);
    }

    public void deleteCoupon(Coupon coupon) {
        couponRepository.delete(coupon);
    }

    public Page<CouponDto> getCoupon(String couponName, String frDt, String toDt, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "couponSdate"));
        Page<Coupon> list = couponRepository.selectCoupon(couponName, frDt, toDt, pageable);
        Page<CouponDto> rtnList = null;

        if(list.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        } else {
            rtnList = list.map(c -> CouponDto.builder()
                    .couponCode(c.getCouponCode())
                    .couponName(c.getCouponName())
                    .couponExpln(c.getCouponExpln())
                    .couponType(c.getCouponType())
                    .couponAmt(c.getCouponAmt())
                    .couponSdate(c.getCouponSdate())
                    .couponEdate(c.getCouponEdate())
                    .build());
        }

        return rtnList;
    }

    public Page<CouponDto> getCouponDownload(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "couponSdate"));
        Page<Coupon> list = couponRepository.selectCouponDownload(pageable);
        Page<CouponDto> rtnList = null;

        if(list.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        } else {
            rtnList = list.map(c -> CouponDto.builder()
                    .couponCode(c.getCouponCode())
                    .couponName(c.getCouponName())
                    .couponExpln(c.getCouponExpln())
                    .couponType(c.getCouponType())
                    .couponAmt(c.getCouponAmt())
                    .couponSdate(c.getCouponSdate())
                    .couponEdate(c.getCouponEdate())
                    .build());
        }

        return rtnList;
    }

    public void procedureExpiredCoupon() {
        couponRepository.procedureExpiredCoupon();
    }
}
