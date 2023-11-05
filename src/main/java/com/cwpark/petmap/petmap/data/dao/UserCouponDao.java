package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.config.exception.NoSuchDataException;
import com.cwpark.petmap.petmap.config.redis.MyRedis;
import com.cwpark.petmap.petmap.data.classid.UserCouponClassId;
import com.cwpark.petmap.petmap.data.domain.Coupon;
import com.cwpark.petmap.petmap.data.domain.Item;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserCoupon;
import com.cwpark.petmap.petmap.data.dto.CouponDto;
import com.cwpark.petmap.petmap.data.dto.UserCouponDto;
import com.cwpark.petmap.petmap.data.dto.UserDto;
import com.cwpark.petmap.petmap.data.persistence.CouponRepository;
import com.cwpark.petmap.petmap.data.persistence.UserCouponRepository;
import com.cwpark.petmap.petmap.data.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
public class UserCouponDao {
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    private final MyRedis myRedis;

    @Autowired
    public UserCouponDao(UserCouponRepository userCouponRepository, MyRedis myRedis, UserRepository userRepository, CouponRepository couponRepository) {
        this.userCouponRepository = userCouponRepository;
        this.myRedis = myRedis;
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
    }

    public void saveUserCoupon(UserCouponDto userCouponDto) {
        UserCouponClassId userCouponClassId = new UserCouponClassId(
                userCouponDto.getUserDto().getUserId()
                , userCouponDto.getCouponDto().getCouponCode()
        );

        if(userCouponRepository.existsById(userCouponClassId)) {
            throw new DataIntegrityViolationException("이미 다운로드한 쿠폰입니다");
        }

        userCouponRepository.save(UserCoupon.builder()
                        .user(User.builder()
                                .userId(userCouponDto.getUserDto().getUserId())
                                .userPassWord("0")
                                .userEmail("0")
                                .userRole("0")
                                .userLoginFailCnt(0)
                                .userBizNo("0")
                                .userBizName("0")
                                .build())
                        .coupon(Coupon.builder()
                                .couponCode(userCouponDto.getCouponDto().getCouponCode())
                                .couponName("0")
                                .couponSdate("0")
                                .couponEdate("0")
                                .couponType("0")
                                .couponAmt(0)
                                .couponExpln("0")
                                .build())
                .build());
    }

    public Page<UserCoupon> getUserCoupon(User user, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "coupon"));

        Page<UserCoupon> userCouponPage = userCouponRepository.getUserCoupon(user, pageable);


        if(userCouponPage.getContent().isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return userCouponPage;
    }

    public List<UserCouponDto> getUserCoupon(User user) {
        List<UserCoupon> list = userCouponRepository.findByUser(user);

        return list.stream().map(u -> UserCouponDto.builder()
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
                .build()).collect(Collectors.toList());
    }

    public boolean useUserCoupon(UserCoupon userCoupon, String type) {
        TransactionStatus transactionStatus = myRedis.startDBTransacton();
        Boolean bool = true;
        UserCouponClassId id = UserCouponClassId.builder()
                .user(userCoupon.getUser().getUserId())
                .coupon(userCoupon.getCoupon().getCouponCode())
                .build();

        try {
            if(myRedis.tryLock("useUserCoupon", TimeUnit.SECONDS, 60, 10)) {

                if(type.equals("-")) {
                    if(userCouponRepository.existsById(id)) {
                        userCouponRepository.delete(userCoupon);
                    } else {
                        bool = false;
                    }
                } else {
                    UserCoupon userCouponRollback = UserCoupon.builder()
                            .user(User.builder().userId(id.getUser()).build())
                            .coupon(Coupon.builder().couponCode(id.getCoupon()).build())
                            .build();

                    userCouponRepository.save(userCouponRollback);

                }

                myRedis.commitDB(transactionStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            myRedis.rollbackDB(transactionStatus);
        } finally {
            if(myRedis.canUnlock("useUserCoupon")) {
                myRedis.unlock("useUserCoupon");
            }
        }

        return bool;
    }
}
