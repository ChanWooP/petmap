package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.classid.SelItemReviewClassId;
import com.cwpark.petmap.petmap.data.classid.UserItemQnaClassId;
import com.cwpark.petmap.petmap.data.domain.Item;
import com.cwpark.petmap.petmap.data.domain.SelItemReview;
import com.cwpark.petmap.petmap.data.domain.UserItemQna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SelItemReviewRepository extends JpaRepository<SelItemReview, SelItemReviewClassId> {
    @Query(value="SELECT IFNULL(MAX(s.review_id)+1, 1) FROM sel_item_review s WHERE s.review_store_id = :store AND s.review_item_id = :item AND s.review_dt = DATE_FORMAT(NOW(), '%Y%m%d')", nativeQuery = true)
    Long getUserOrderId(@Param("store") String store, @Param("item") String item);

    Page<SelItemReview> findByItem(Item item, Pageable pageable);
}
