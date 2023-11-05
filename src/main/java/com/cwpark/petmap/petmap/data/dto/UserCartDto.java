package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCartDto {

  private String user;

  private ItemDto item;

  private int cartCnt;
}
