package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelOrderSaleDto {

  private SelOrderDto selOrderDto;

  private int saleCnt;

  private int saleAmt;

}
