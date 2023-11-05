package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.classid.StockClassId;
import com.cwpark.petmap.petmap.data.classid.UserOrderClassId;
import com.cwpark.petmap.petmap.data.domain.Stock;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface UserOrderRepository extends JpaRepository<UserOrder, UserOrderClassId> {
    @Query(value="SELECT IFNULL(MAX(u.ord_id)+1, 1) FROM user_order u WHERE u.ord_user_id = :user AND u.ord_dt = DATE_FORMAT(NOW(), '%Y%m%d')", nativeQuery = true)
    Long getUserOrderId(@Param("user") String user);

    Page<UserOrder> findByUserAndOrdDtBetween(User user, String frDt, String toDt, Pageable pageable);
}
