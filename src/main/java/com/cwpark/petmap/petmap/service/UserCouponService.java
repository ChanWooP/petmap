package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.UserCouponDao;
import com.cwpark.petmap.petmap.data.domain.Coupon;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserCoupon;
import com.cwpark.petmap.petmap.data.dto.CouponDto;
import com.cwpark.petmap.petmap.data.dto.UserCouponDto;
import com.cwpark.petmap.petmap.data.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCouponService {
    private final UserCouponDao userCouponDao;

    @Autowired
    public UserCouponService(UserCouponDao userCouponDao) {
        this.userCouponDao = userCouponDao;
    }

    public void saveUserCoupon(UserCouponDto userCouponDto) {
        userCouponDao.saveUserCoupon(userCouponDto);
    }

    public Page<UserCouponDto> getUserCoupon(String userId, int page) {
        User user = User.builder()
                .userId(userId)
                .userPassWord("0")
                .userEmail("0")
                .userRole("0")
                .userLoginFailCnt(0)
                .userBizNo("0")
                .userBizName("0")
                .build();

        Page<UserCoupon> list = userCouponDao.getUserCoupon(user, page);
        Page<UserCouponDto> rtnList = null;

        if(list.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        } else {
            rtnList = list.map(u -> UserCouponDto.builder()
                    .userDto(UserDto.builder()
                            .userId(u.getUser().getUserId())
                            .userPassWord(u.getUser().getUserPassWord())
                            .userName(u.getUser().getUserName())
                            .userEmail(u.getUser().getUserEmail())
                            .userPhone(u.getUser().getUserPhone())
                            .userZipcode(u.getUser().getUserZipcode())
                            .userAddress1(u.getUser().getUserAddress1())
                            .userAddress2(u.getUser().getUserAddress2())
                            .userRole(u.getUser().getUserRole())
                            .userLoginFailCnt(u.getUser().getUserLoginFailCnt())
                            .userBizNo(u.getUser().getUserBizNo())
                            .userBirth(u.getUser().getUserBirth())
                            .userSex(u.getUser().getUserSex())
                            .oAuthType(u.getUser().getOAuthType())
                            .userBizNo(u.getUser().getUserBizNo())
                            .userBizName(u.getUser().getUserBizName())
                            .build())
                    .couponDto(CouponDto.builder()
                            .couponCode(u.getCoupon().getCouponCode())
                            .couponName(u.getCoupon().getCouponName())
                            .couponExpln(u.getCoupon().getCouponExpln())
                            .couponType(u.getCoupon().getCouponType())
                            .couponAmt(u.getCoupon().getCouponAmt())
                            .couponSdate(u.getCoupon().getCouponSdate())
                            .couponEdate(u.getCoupon().getCouponEdate())
                            .build())
                    .build());
        }

        return rtnList;
    }

    public List<UserCouponDto> getUserCoupon(String userId) {
        return userCouponDao.getUserCoupon(User.builder().userId(userId).build());
    }

    public boolean useUserCoupon(UserCouponDto userCouponDto, String type) {
        UserCoupon userCoupon = UserCoupon.builder()
                .user(User.builder().userId(userCouponDto.getUserDto().getUserId()).build())
                .coupon(Coupon.builder().couponCode(userCouponDto.getCouponDto().getCouponCode()).build())
                .build();

        return userCouponDao.useUserCoupon(userCoupon, type);
    }
}
