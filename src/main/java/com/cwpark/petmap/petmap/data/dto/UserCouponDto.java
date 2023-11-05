package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCouponDto {
    private UserDto userDto;

    private CouponDto couponDto;
}
