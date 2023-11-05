package com.cwpark.petmap.petmap.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPointHisDto {
    private UserDto userDto;

    private int pointId;

    private int pointNum;

    private String pointExpln;

    private String pointReason;
}
