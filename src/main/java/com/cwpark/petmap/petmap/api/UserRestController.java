package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.UserDto;
import com.cwpark.petmap.petmap.data.dto.UserPointHisDto;
import com.cwpark.petmap.petmap.data.enums.OAuthType;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"사용자 API"})
@RestController
@Log
@RequestMapping("/api")
public class UserRestController {

  private final UserService userService;
  private final UserPointHisService userPointHisService;

  @Value("${kakao.default.password}")
  private String kakaoPassword;

  @Autowired
  public UserRestController(UserService userService, UserPointHisService userPointHisService) {
    this.userService = userService;
    this.userPointHisService = userPointHisService;
  }

  @GetMapping("v1/user/check/id/{userId}")
  @Operation(summary = "사용자 ID 중복 체크", description = "사용자 아이디를 기반으로 중복 체크를 합니다")
  public ResponseEntity<ApiResponse<Object>> existsUserId(@ApiParam(value = "사용자 아이디") @PathVariable("userId") String userId) {
    Boolean result = userService.existsUser(userId, "Id");

    ApiResponse ar = ApiResponse.builder()
        .result(result)
        .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
        .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
        .build();

    return new ResponseEntity<>(ar, HttpStatus.OK);
  }

  @GetMapping("v1/user/check/email/{email}")
  @Operation(summary = "사용자 Email 중복 체크", description = "사용자 이메일을 기반으로 중복 체크를 합니다")
  public ResponseEntity<ApiResponse<Object>> existsUserEmail(@ApiParam(value = "사용자 이메일") @PathVariable("email") String userEmail) {
    Boolean result = userService.existsUser(userEmail, "Email");

    ApiResponse ar = ApiResponse.builder()
        .result(result)
        .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
        .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
        .build();

    return new ResponseEntity<>(ar, HttpStatus.OK);
  }

  @GetMapping("v1/user/{type}/{user}")
  @Operation(summary = "사용자 조회", description = "사용자를 type(id 또는 email)에 따라 조회합니다")
  public ResponseEntity<ApiResponse<Object>> selectUser(@ApiParam(value = "타입") @PathVariable String type, @ApiParam(value = "사용자") @PathVariable String user) {
    UserDto result = userService.getUser(user, type);

    ApiResponse ar = ApiResponse.builder()
            .result(result)
            .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
            .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
            .build();

    return new ResponseEntity<>(ar, HttpStatus.OK);
  }

  @PostMapping("v1/user")
  @Operation(summary = "사용자 추가", description = "사용자를 추가합니다")
  public ResponseEntity<ApiResponse<Object>> postUser(@ApiParam(value = "사용자") @RequestBody UserDto userDto) {
    UserDto result = userService.saveUser(userDto);
    userPointHisService.postUserPointHis(UserPointHisDto.builder()
                    .userDto(userDto)
                    .pointId(0)
                    .pointExpln("신규가입 포인트")
                    .pointReason("newUser")
                    .pointNum(5000)
            .build());

    ApiResponse ar = ApiResponse.builder()
        .result(result)
        .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
        .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
        .build();

    return new ResponseEntity<>(ar, HttpStatus.OK);
  }

  @PutMapping("v1/user")
  @Operation(summary = "사용자 수정", description = "사용자를 수정합니다")
  public ResponseEntity<ApiResponse<Object>> putUser(@ApiParam(value = "사용자") @RequestBody UserDto userDto) {
    if(userDto.getOAuthType().equals(OAuthType.KAKAO)) {
      userDto.setUserPassWord(kakaoPassword);
    }

    UserDto result = userService.saveUser(userDto);

    ApiResponse ar = ApiResponse.builder()
        .result(result)
        .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
        .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
        .build();

    return new ResponseEntity<>(ar, HttpStatus.OK);
  }

}
