package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.domain.Coupon;
import com.cwpark.petmap.petmap.data.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, String> {
    @Query (value = "SELECT c FROM Coupon c " +
                    " WHERE c.couponName LIKE %:couponName% " +
                    "   AND (:frDt BETWEEN c.couponSdate AND c.couponEdate OR " +
                    "        :toDt BETWEEN c.couponSdate AND c.couponEdate)"
    )
    Page<Coupon> selectCoupon(@Param("couponName") String couponName, @Param("frDt") String frDt, @Param("toDt") String toDt, Pageable pageable);

    @Query (value = "SELECT c FROM Coupon c " +
            " WHERE DATE_FORMAT(NOW(), '%Y%m%d') BETWEEN c.couponSdate AND c.couponEdate "
    )
    Page<Coupon> selectCouponDownload(Pageable pageable);

    List<Coupon> findByCouponName(String s);

    @Query(value = "{ CALL PROCEDURE_EXPIRED_COUPON() }", nativeQuery = true)
    void procedureExpiredCoupon();
}
