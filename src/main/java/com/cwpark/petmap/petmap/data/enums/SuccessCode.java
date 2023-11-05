package com.cwpark.petmap.petmap.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    SELECT_SUCCESS(200, "200", "조회 성공")
  , DELETE_SUCCESS(200, "200", "삭제 성공")
  , INSERT_SUCCESS(201, "201", "삽입 성공")
  , UPDATE_SUCCESS(204, "204", "수정 성공")
  ;

  private final int status;

  private final String code;

  private final String message;


}
