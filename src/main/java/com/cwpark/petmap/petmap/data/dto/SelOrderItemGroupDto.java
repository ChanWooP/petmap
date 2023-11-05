package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelOrderItemGroupDto {
  private String itemId;
  private String itemName;
  private int ordCnt;
  private int ordAmt;
}
