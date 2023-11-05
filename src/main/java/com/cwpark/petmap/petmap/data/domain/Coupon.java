package com.cwpark.petmap.petmap.data.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate  // 변경된 필드만 적용
@DynamicInsert  // 같음
@Table(name = "MST_COUPON")
public class Coupon extends Base{
    @Id
    @NotBlank
    @Column(name = "COUPON_CODE")
    private String couponCode;

    @NotBlank
    @Column(name = "COUPON_NAME")
    private String couponName;

    @NotBlank
    @Column(name = "COUPON_EXPLN")
    private String couponExpln;

    @NotBlank
    @Column(name = "COUPON_TYPE")
    private String couponType;

    @NotNull
    @Column(name = "COUPON_AMT")
    private int couponAmt;

    @NotBlank
    @Column(name = "COUPON_SDATE")
    private String couponSdate;

    @NotBlank
    @Column(name = "COUPON_EDATE")
    private String couponEdate;

}
