package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.classid.SelOrderClassId;
import com.cwpark.petmap.petmap.data.classid.SelOrderSaleClassId;
import com.cwpark.petmap.petmap.data.domain.SelOrder;
import com.cwpark.petmap.petmap.data.domain.SelOrderItem;
import com.cwpark.petmap.petmap.data.domain.SelOrderSale;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.dto.SelOrderSaleGroupDto;
import com.cwpark.petmap.petmap.data.dto.SelOrderSaleGroupInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SelOrderSaleRepository extends JpaRepository<SelOrderSale, SelOrderSaleClassId> {
    @Query(value="SELECT s FROM SelOrderSale s WHERE s.selOrder.user = :user AND s.selOrder.ordDt BETWEEN :frDt AND :toDt")
    Page<SelOrderSale> getListSelOrderSale(@Param("user") String user, @Param("frDt") String frDt, @Param("toDt") String toDt, Pageable pageable);

    @Query(value=" SELECT DATE_FORMAT(ORD_DT, '%Y%m') AS saleDt " +
            "           , SUM(SALE_CNT) AS saleCnt " +
            "           , SUM(SALE_AMT) AS saleAmt " +
            "        FROM sel_order_sale " +
            "       WHERE ORD_USER_ID = :store " +
            "         AND ORD_DT BETWEEN :frDt AND :toDt " +
            "       GROUP BY DATE_FORMAT(ORD_DT, '%Y%m')"
            ,countQuery=" SELECT COUNT(*) " +
            "        FROM sel_order_sale " +
            "       WHERE ORD_USER_ID = :store " +
            "         AND ORD_DT BETWEEN :frDt AND :toDt " +
            "       GROUP BY DATE_FORMAT(ORD_DT, '%Y%m')"
            ,nativeQuery = true
    )

    Page<SelOrderSaleGroupInterface> findBySaleMonth(String store, String frDt, String toDt, Pageable pageable);
}
