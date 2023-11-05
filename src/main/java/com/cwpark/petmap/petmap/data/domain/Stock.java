package com.cwpark.petmap.petmap.data.domain;

import com.cwpark.petmap.petmap.data.classid.StockClassId;
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
@Table(name = "SEL_ITEM_STOCK")
@IdClass(StockClassId.class)
public class Stock extends Base{

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "STOCK_USER_ID", referencedColumnName = "SEL_USER_ID"),
            @JoinColumn(name = "STOCK_ITEM_ID", referencedColumnName = "SEL_ITEM_ID")
    })
    private Item item;

    @Id
    @Column(name = "STOCK_DT")
    private String stockDt;

    @Id
    @Column(name = "STOCK_ID")
    private Long stockId;

    @Column(name = "STOCK_QTY")
    private int stockQty;

    @Column(name = "STOCK_EXPLN")
    private String stockExpln;
}
