package com.cwpark.petmap.petmap.data.dto;

import com.cwpark.petmap.petmap.data.domain.Category;
import com.cwpark.petmap.petmap.data.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

  private String user;

  private String selItemId;

  private String selItemName;

  private Long category;

  private String selMiniImg;

  private String selMainImg;

  private String selExpln;

  private int selItemPrice;

  private int selDeilPrice;

  private float selStarPoint;

  private int selSaleCount;

  private int selHeartCount;

  private int selStockCount;

  private double selStarPointAvg;

  private int selStarPointCnt;
}
