package com.cwpark.petmap.petmap.data.dto;

import com.cwpark.petmap.petmap.data.enums.OAuthType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponDto {

  @NotBlank(message = "쿠폰번호는 필수 입력사항입니다")
  private String couponCode;

  @NotBlank(message = "쿠폰명은 필수 입력사항입니다")
  private String couponName;

  @NotBlank(message = "쿠폰설명은 필수 입력사항입니다")
  private String couponExpln;

  @NotBlank(message = "쿠폰타입은 필수 입력사항입니다")
  private String couponType;

  @NotNull(message = "쿠폰 금액 및 할인율은 필수 입력사항입니다")
  private int couponAmt;

  @NotBlank(message = "쿠폰시작일자는 필수 입력사항입니다")
  private String couponSdate;

  @NotBlank(message = "쿠폰종료일자는 필수 입력사항입니다")
  private String couponEdate;
}
