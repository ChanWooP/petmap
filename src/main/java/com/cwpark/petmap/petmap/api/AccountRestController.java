package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.UserDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.UserPointHisService;
import com.cwpark.petmap.petmap.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"사용자 API"})
@RestController
@Log
@RequestMapping("/api")
public class AccountRestController {

  private final UserService userService;
  private final UserPointHisService userPointHisService;

  @Value("${kakao.default.password}")
  private String kakaoPassword;

  @Autowired
  public AccountRestController(UserService userService, UserPointHisService userPointHisService) {
    this.userService = userService;
    this.userPointHisService = userPointHisService;
  }
  @GetMapping("v1/admin/user/{userId}/{page}")
  @Operation(summary = "사용자 목록 조회", description = "사용자 목록을 조회합니다")
  public ResponseEntity<ApiResponse<Object>> selectUserList(@ApiParam(value = "사용자ID") @PathVariable String userId, @ApiParam(value = "페이지") @PathVariable int page) {
    Page<UserDto> result = userService.getUser(userId.equals("all") ? "" : userId, page);

    ApiResponse ar = ApiResponse.builder()
        .result(result)
        .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
        .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
        .build();

    return new ResponseEntity<>(ar, HttpStatus.OK);
  }

}
