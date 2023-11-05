package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelOrderSaleGroupDto {

private String saleDt;
private int saleCnt;
private int saleAmt;

}
