package com.cwpark.petmap.petmap.data.domain;

import com.cwpark.petmap.petmap.data.classid.ItemClassId;
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
@Table(name = "SEL_ITEM")
@IdClass(ItemClassId.class)
public class Item extends Base{
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="SEL_USER_ID")
    private User user;

    @Id
    @Column(name="SEL_ITEM_ID")
    private String selItemId;

    @Column(name="SEL_ITEM_NAME")
    private String selItemName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="SEL_CATEGORY_ID")
    private Category category;

    @Column(name="SEL_MINI_IMG")
    private String selMiniImg;

    @Column(name="SEL_MAIN_IMG")
    private String selMainImg;

    @Column(name="SEL_EXPLN")
    private String selExpln;

    @Column(name="SEL_ITEM_PRICE")
    private int selItemPrice;

    @Column(name="SEL_DEIL_PRICE")
    private int selDeilPrice;

    @Column(name="SEL_STAR_POINT")
    private float selStarPoint;

    @Column(name="SEL_SALE_COUNT")
    private int selSaleCount;

    @Column(name="SEL_HEART_COUNT")
    private int selHeartCount;

    @Column(name="SEL_STOCK_COUNT")
    private int selStockCount;

    @Column(name="SEL_STAR_POINT_AVG")
    private double selStarPointAvg;

    @Column(name="SEL_STAR_POINT_CNT")
    private int selStarPointCnt;

}
