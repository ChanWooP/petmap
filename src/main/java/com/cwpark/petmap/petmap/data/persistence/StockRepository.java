package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.classid.StockClassId;
import com.cwpark.petmap.petmap.data.domain.Category;
import com.cwpark.petmap.petmap.data.domain.Item;
import com.cwpark.petmap.petmap.data.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, StockClassId>, QuerydslPredicateExecutor<Stock> {
    @Query(value="SELECT IFNULL(MAX(s.stock_id)+1, 1) FROM sel_item_stock s " +
            "WHERE s.stock_user_id = :user" +
            "  AND s.stock_item_id = :item" +
            "  AND s.stock_dt = :dt"
            , nativeQuery = true)
    Long getStockId(@Param("user") String user, @Param("item") String item, @Param("dt") String dt);
}
