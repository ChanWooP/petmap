package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.UserCouponDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.UserCouponService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserCouponRestController {
    private final UserCouponService userCouponService;

    @Autowired
    public UserCouponRestController(UserCouponService userCouponService) {
        this.userCouponService = userCouponService;
    }

    @PostMapping("v1/usercoupon")
    @Operation(summary = "쿠폰 추가", description = "쿠폰를 추가합니다")
    public ResponseEntity<ApiResponse<Object>> postUserCoupon(@ApiParam(value = "쿠폰") @RequestBody UserCouponDto userCouponDto) {
        userCouponService.saveUserCoupon(userCouponDto);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/usercoupon/{userId}/{page}")
    @Operation(summary = "쿠폰 조회", description = "쿠폰을 조회합니다")
    public ResponseEntity<ApiResponse<Object>> getCouponDownload(@PathVariable("userId") String userId, @PathVariable("page") int page) {
        Page<UserCouponDto> result = userCouponService.getUserCoupon(userId, page);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/usercoupon/list/{userId}")
    public ResponseEntity<ApiResponse<Object>> getCouponList(@PathVariable("userId") String userId) {
        List<UserCouponDto> result = userCouponService.getUserCoupon(userId);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

}
