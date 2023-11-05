package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.CouponDao;
import com.cwpark.petmap.petmap.data.domain.Coupon;
import com.cwpark.petmap.petmap.data.dto.CouponDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CouponService {
    private final CouponDao couponDao;

    @Autowired
    public CouponService(CouponDao couponDao) {
        this.couponDao = couponDao;
    }

    public Page<CouponDto> getCoupon(String couponName, String frDt, String toDt, int page) {
        return couponDao.getCoupon(couponName, frDt, toDt, page);
    }

    public Page<CouponDto> getCouponDownload(int page) {
        return couponDao.getCouponDownload(page);
    }

    public void postCoupon(CouponDto couponDto) {
        Coupon coupon = Coupon.builder()
                .couponCode(couponDto.getCouponCode())
                .couponName(couponDto.getCouponName())
                .couponExpln(couponDto.getCouponExpln())
                .couponType(couponDto.getCouponType())
                .couponAmt(couponDto.getCouponAmt())
                .couponSdate(couponDto.getCouponSdate())
                .couponEdate(couponDto.getCouponEdate())
                .build();

        couponDao.postCoupon(coupon);
    }

    public void putCoupon(CouponDto couponDto) {
        Coupon coupon = Coupon.builder()
                .couponCode(couponDto.getCouponCode())
                .couponName(couponDto.getCouponName())
                .couponExpln(couponDto.getCouponExpln())
                .couponType(couponDto.getCouponType())
                .couponAmt(couponDto.getCouponAmt())
                .couponSdate(couponDto.getCouponSdate())
                .couponEdate(couponDto.getCouponEdate())
                .build();

        couponDao.putCoupon(coupon);
    }

    public void deleteCoupon(CouponDto couponDto) {
        Coupon coupon = Coupon.builder()
                .couponCode(couponDto.getCouponCode())
                .couponName(couponDto.getCouponName())
                .couponExpln(couponDto.getCouponExpln())
                .couponType(couponDto.getCouponType())
                .couponAmt(couponDto.getCouponAmt())
                .couponSdate(couponDto.getCouponSdate())
                .couponEdate(couponDto.getCouponEdate())
                .build();

        couponDao.deleteCoupon(coupon);
    }

    public void procedureExpiredCoupon() {
        couponDao.procedureExpiredCoupon();
    }
}
