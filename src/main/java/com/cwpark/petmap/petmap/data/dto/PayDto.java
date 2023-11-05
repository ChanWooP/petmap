package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayDto {

  private PayUserDto user;

  private PayAddressDto address;

  private List<PayItemDto> item;

  private PayDcDto dc;

  private PayPayDto pay;

}
