package com.cwpark.petmap.petmap.data.domain;

import com.cwpark.petmap.petmap.data.classid.SelOrderSaleClassId;
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
@Table(name = "sel_order_sale")
@IdClass(SelOrderSaleClassId.class)
public class SelOrderSale extends Base{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
              @JoinColumn(name="ORD_USER_ID", referencedColumnName = "ORD_USER_ID")
            , @JoinColumn(name="ORD_DT", referencedColumnName = "ORD_DT")
            , @JoinColumn(name="ORD_ID", referencedColumnName = "ORD_ID")
    })
    private SelOrder selOrder;

    @Column(name = "SALE_CNT")
    private int saleCnt;

    @Column(name = "SALE_AMT")
    private int saleAmt;
}
