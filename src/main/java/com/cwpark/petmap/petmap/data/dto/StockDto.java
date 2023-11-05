package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDto {

  private ItemDto itemDto;

  private String stockDt;

  private Long stockId;

  private int stockQty;

  private String stockExpln;
}
