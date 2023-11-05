package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.classid.UserCouponClassId;
import com.cwpark.petmap.petmap.data.domain.Coupon;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, UserCouponClassId> {
    @Query(" SELECT u FROM UserCoupon u WHERE u.user = :user" +
            " AND DATE_FORMAT(NOW(), '%Y%m%d') BETWEEN u.coupon.couponSdate AND u.coupon.couponEdate "
    )
    Page<UserCoupon> getUserCoupon(@Param(value = "user") User user, Pageable pageable);

    List<UserCoupon> findByUser(User user);
}
