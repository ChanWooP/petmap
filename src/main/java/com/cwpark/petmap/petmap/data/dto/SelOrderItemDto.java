package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelOrderItemDto {

  private SelOrderDto selOrderDto;

  private ItemDto itemDto;

  private int ordCnt;

  private int ordAmt;

  private String itemReviewYn;

}
