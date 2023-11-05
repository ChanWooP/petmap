package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.classid.SelOrderClassId;
import com.cwpark.petmap.petmap.data.classid.UserOrderClassId;
import com.cwpark.petmap.petmap.data.domain.SelOrder;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SelOrderRepository extends JpaRepository<SelOrder, SelOrderClassId> {
    @Query(value="SELECT IFNULL(MAX(u.ord_id)+1, 1) FROM sel_order u WHERE u.ord_user_id = :user AND u.ord_dt = DATE_FORMAT(NOW(), '%Y%m%d')", nativeQuery = true)
    Long getUserOrderId(@Param("user") String user);

    Page<SelOrder> findByUserAndOrdDtBetween(User user, String frDt, String toDt, Pageable pageable);

    List<SelOrder> findByOrdDtAndUserOrder(String ordDt, UserOrder userOrder);
}
