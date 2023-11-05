package com.cwpark.petmap.petmap.data.domain;

import com.cwpark.petmap.petmap.data.classid.SelOrderItemClassId;
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
@Table(name = "sel_order_item")
@IdClass(SelOrderItemClassId.class)
public class SelOrderItem extends Base{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
              @JoinColumn(name="ORD_USER_ID", referencedColumnName = "ORD_USER_ID")
            , @JoinColumn(name="ORD_DT", referencedColumnName = "ORD_DT")
            , @JoinColumn(name="ORD_ID", referencedColumnName = "ORD_ID")
    })
    private SelOrder selOrder;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
              @JoinColumn(name="ITEM_USER_ID", referencedColumnName = "SEL_USER_ID")
            , @JoinColumn(name="ITEM_ID", referencedColumnName = "SEL_ITEM_ID")
    })
    private Item item;

    @Column(name = "ORD_CNT")
    private int ordCnt;

    @Column(name = "ORD_AMT")
    private int ordAmt;

    @Column(name = "ITEM_REVIEW_YN")
    private String itemReviewYn;
}
