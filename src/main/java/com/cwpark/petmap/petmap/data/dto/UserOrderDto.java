package com.cwpark.petmap.petmap.data.dto;

import com.cwpark.petmap.petmap.data.domain.User;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDto {

  private String user;

  private String ordDt;

  private Long ordId;

  private String ordAddress;

  private String ordName;

  private String ordPhone;

  private int ordTotAmt;

  private int ordCouDc;

  private int ordPoiDc;

  private int ordDelAmt;

  private int ordNetAmt;
}
