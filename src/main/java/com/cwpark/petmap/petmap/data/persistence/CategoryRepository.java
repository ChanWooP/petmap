package com.cwpark.petmap.petmap.data.persistence;

import com.cwpark.petmap.petmap.data.domain.Category;
import com.cwpark.petmap.petmap.data.domain.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryDepthOrderByCategoryId(int depth);
    List<Category> findByCategoryParentAndCategoryDepthOrderByCategoryId(Long parent, int depth);

    @Query(value =  " DELETE FROM mst_category " +
                    "  WHERE category_id IN ( " +
                    " 	SELECT A.category_id " +
                    " 	  FROM mst_category A " +
                    " 	  LEFT OUTER JOIN mst_category B " +
                    " 	    ON A.category_parent = B.category_id " +
                    " 	 WHERE A.Category_id = :categoryId " +
                    " 	    OR A.category_parent = :categoryId " +
                    " 	    OR B.category_parent = :categoryId ) "
            , nativeQuery = true
    )
    void deleteCategory(@Param("categoryId") Long categoryId);

    @Query(value =  "SELECT CONCAT(b.CATEGORY_PARENT, 'c', b.CATEGORY_ID, 'c', a.CATEGORY_ID) " +
                    "  FROM mst_category a " +
                    "  JOIN mst_category b " +
                    "    ON a.category_parent = b.CATEGORY_ID " +
                    " WHERE a.category_id = :categoryId "
            ,nativeQuery = true
    )
    String findCategory(@Param("categoryId") Long categoryId);

}
