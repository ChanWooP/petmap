package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayPayDto {

  private int itemPrice;

  private int point;

  private int coupon;

  private int deliPrice;

  private int totPrice;

}
