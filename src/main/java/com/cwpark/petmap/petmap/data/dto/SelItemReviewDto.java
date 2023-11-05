package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelItemReviewDto {

  private ItemDto item;

  private String reviewDt;

  private Long reviewId;

  private String user;

  private int reviewStarPoint;

  private String reviewText;

  private String ordDt;

  private Long ordId;

}
