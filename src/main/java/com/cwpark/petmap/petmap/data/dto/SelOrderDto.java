package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelOrderDto {

  private String user;

  private String ordDt;

  private Long ordId;

  private UserOrderDto userOrderDto;

  private String ordStatus;

  private String ordInvoice;

  private int ordTotAmt;

  private int ordDelAmt;
}
