package com.cwpark.petmap.petmap.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

  private T result;

  private int resultCode;

  private String resultMsg;

}
