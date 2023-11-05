package com.cwpark.petmap.petmap.data.domain;

import com.cwpark.petmap.petmap.data.classid.UserOrderClassId;
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
@Table(name = "user_order")
@IdClass(UserOrderClassId.class)
public class UserOrder extends Base{

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

    @Column(name = "ORD_ADDRESS")
    private String ordAddress;

    @Column(name = "ORD_NAME")
    private String ordName;

    @Column(name = "ORD_PHONE")
    private String ordPhone;

    @Column(name = "ORD_TOT_AMT")
    private int ordTotAmt;

    @Column(name = "ORD_COU_DC")
    private int ordCouDc;

    @Column(name = "ORD_POI_DC")
    private int ordPoiDc;

    @Column(name = "ORD_DEL_AMT")
    private int ordDelAmt;

    @Column(name = "ORD_NET_AMT")
    private int ordNetAmt;
}
