package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddressDto {

  private Long addressId;

  private String user;

  private String addressUserPhone;

  private String addressUserZipcode;

  private String addressUserAddress1;

  private String addressUserAddress2;

  private String addressUserName;
}
