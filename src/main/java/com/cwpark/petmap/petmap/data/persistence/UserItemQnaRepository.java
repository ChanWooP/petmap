package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.classid.StockClassId;
import com.cwpark.petmap.petmap.data.classid.UserItemQnaClassId;
import com.cwpark.petmap.petmap.data.domain.Item;
import com.cwpark.petmap.petmap.data.domain.Stock;
import com.cwpark.petmap.petmap.data.domain.UserItemQna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface UserItemQnaRepository extends JpaRepository<UserItemQna, UserItemQnaClassId> {
    @Query(value="SELECT IFNULL(MAX(u.item_qna_id)+1, 1) FROM user_item_qna u " +
            "WHERE u.item_qna_user_id = :user" +
            "  AND u.item_qna_store_id = :store" +
            "  AND u.item_qna_item_id = :item"
            , nativeQuery = true)
    Long getUserItemQnaId(@Param("user") String user, @Param("store") String store, @Param("item") String item);

    Page<UserItemQna> findByItem(Item item, Pageable pageable);
}
