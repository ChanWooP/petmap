package com.cwpark.petmap.petmap.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  /**
   * ******************************* Global Error CodeList ***************************************
   * HTTP Status Code
   * 400 : Bad Request
   * 401 : Unauthorized
   * 403 : Forbidden
   * 404 : Not Found
   * 500 : Internal Server Error
   * *********************************************************************************************
   */
    BAD_REQUEST_ERROR(400, "G001", "잘못된 서버 요청입니다")
  , REQUEST_BODY_MISSING_ERROR(400, "G002", "데이터가 존재하지 않습니다")
  , INVALID_TYPE_VALUE(400, "G003", "유요하지 않은 타입입니다")
  , MISSING_REQUEST_PARAMETER_ERROR(400, "G004", "데이터가 전달되지 않았습니다")
  , IO_ERROR(400, "G005", "입/출력 값이 유요하지 않습니다")
  , JSON_PARSE_ERROR(400, "G006", "JSON 파싱 실패했습니다")
  , JACKSON_PROCESS_ERROR(400, "G007", "JACKSON 프로세싱 실패했습니다")
  , FORBIDDEN_ERROR(403, "G008", "권한이 없습니다")
  , NOT_FOUND_ERROR(404, "G009", "서버로 요청한 리소스가 존재하지 않습니다")
  , NULL_POINT_ERROR(404, "G010", "Null 값이 존재합니다")
  , NOT_VALID_ERROR(404, "G011", "요청 값이 유효하지 않습니다")
  , NOT_VALID_HEADER_ERROR(404, "G012", "헤더에 데이터가 존재하지 않습니다")
  , INTERNAL_SERVER_ERROR(500, "G999", "서버 오류입니다")
  , EMPTYRESULT_DATAACCESS_ERROR(400, "G013", "데이터가 존재하지 않습니다")
  , DATAINTEGRITY_VIOLATION_ERROR(400, "G014", "데이터가 중복되었습니다")
  , COUNT_ERROR(400, "G015", "수량이 초과되었습니다")
  , NOSUCHDATA_ERROR(400, "G016", "찾는 데이터가 없습니다")

  /**
   * ******************************* Custom Error CodeList ***************************************
   */
  , INSERT_ERROR(200, "9999", "삽입 오류")
  , UPDATE_ERROR(200, "9999", "수정 오류")
  , DELETE_ERROR(200, "9999", "삭제 오류")
  ;


  private final int status;

  private final String divisionCode;

  private final String message;
}
