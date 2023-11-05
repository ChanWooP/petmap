package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.classid.SelOrderClassId;
import com.cwpark.petmap.petmap.data.classid.SelOrderItemClassId;
import com.cwpark.petmap.petmap.data.domain.SelOrder;
import com.cwpark.petmap.petmap.data.domain.SelOrderItem;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.dto.SelOrderItemGroupDto;
import com.cwpark.petmap.petmap.data.dto.SelOrderItemGroupInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface SelOrderItemRepository extends JpaRepository<SelOrderItem, SelOrderItemClassId> {

    List<SelOrderItem> findBySelOrder(SelOrder selOrder);

    @Query(value="SELECT s FROM SelOrderItem s WHERE s.selOrder.user = :user AND s.selOrder.ordDt BETWEEN :frDt AND :toDt")
    Page<SelOrderItem> getListSelOrderItem(@Param("user") User user, @Param("frDt") String frDt, @Param("toDt") String toDt, Pageable pageable);

    @Query(value = " SELECT A.ITEM_ID AS itemId " +
            "             , B.SEL_ITEM_NAME AS itemName " +
            "             , SUM(A.ORD_CNT) AS ordCnt " +
            "             , SUM(A.ORD_AMT) AS ordAmt " +
            "          FROM sel_order_item A " +
            "          JOIN sel_item B " +
            "            ON A.ITEM_USER_ID = B.SEL_USER_ID " +
            "           AND A.ITEM_ID = B.SEL_ITEM_ID " +
            "         WHERE A.ORD_USER_ID = :store " +
            "           AND A.ORD_DT BETWEEN :frDt AND :toDt " +
            "         GROUP BY A.ITEM_ID "
            , countQuery = " SELECT COUNT(*) " +
            "          FROM sel_order_item A " +
            "          JOIN sel_item B " +
            "            ON A.ITEM_USER_ID = B.SEL_USER_ID " +
            "           AND A.ITEM_ID = B.SEL_ITEM_ID " +
            "         WHERE A.ORD_USER_ID = :store " +
            "           AND A.ORD_DT BETWEEN :frDt AND :toDt " +
            "         GROUP BY A.ITEM_ID "
            , nativeQuery = true)
    Page<SelOrderItemGroupInterface> findByItemGroup(String store, String frDt, String toDt, Pageable pageable);
}
