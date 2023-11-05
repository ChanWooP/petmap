package com.cwpark.petmap.petmap.data.domain;

import com.cwpark.petmap.petmap.data.classid.SelItemReviewClassId;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate  // 변경된 필드만 적용
@DynamicInsert  // 같음
@Table(name = "sel_item_review")
@IdClass(SelItemReviewClassId.class)
public class SelItemReview extends Base{

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
              @JoinColumn(name = "REVIEW_STORE_ID", referencedColumnName = "SEL_USER_ID")
            , @JoinColumn(name = "REVIEW_ITEM_ID", referencedColumnName = "SEL_ITEM_ID")
    })
    private Item item;

    @Id
    @Column(name = "REVIEW_DT")
    private String reviewDt;

    @Id
    @Column(name = "REVIEW_ID")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "REVIEW_USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @Column(name = "REVIEW_STAR_POINT")
    private int reviewStarPoint;

    @Column(name = "REVIEW_TEXT")
    private String reviewText;

}
