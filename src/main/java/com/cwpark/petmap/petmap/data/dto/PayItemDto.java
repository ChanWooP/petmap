package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayItemDto {

  private String store;

  private String itemId;

  private int itemPrice;

  private int itemDeli;

  private int itemCnt;

}
