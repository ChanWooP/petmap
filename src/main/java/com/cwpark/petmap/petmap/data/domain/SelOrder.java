package com.cwpark.petmap.petmap.data.domain;

import com.cwpark.petmap.petmap.data.classid.SelOrderClassId;
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
@Table(name = "sel_order")
@IdClass(SelOrderClassId.class)
public class SelOrder extends Base{

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORD_USER_ID")
    private User user;

    @Id
    @Column(name = "ORD_DT")
    private String ordDt;

    @Id
    @Column(name = "ORD_ID")
    private Long ordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
              @JoinColumn(name="USER_ORD_USER_ID", referencedColumnName = "ORD_USER_ID")
            , @JoinColumn(name="USER_ORD_DT", referencedColumnName = "ORD_DT")
            , @JoinColumn(name="USER_ORD_ID", referencedColumnName = "ORD_ID")
    })
    private UserOrder userOrder;

    @Column(name = "ORD_STATUS")
    private String ordStatus;

    @Column(name = "ORD_INVOICE")
    private String ordInvoice;

    @Column(name = "ORD_TOT_AMT")
    private int ordTotAmt;

    @Column(name = "ORD_DEL_AMT")
    private int ordDelAmt;
}
