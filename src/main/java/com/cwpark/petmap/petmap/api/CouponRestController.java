package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.CouponDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.CouponService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CouponRestController {
    private final CouponService couponService;

    @Autowired
    public CouponRestController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("v1/admin/coupon/{couponName}/{frDt}/{toDt}/{page}")
    @Operation(summary = "쿠폰 조회", description = "쿠폰을 조회합니다")
    public ResponseEntity<ApiResponse<Object>> getCoupon(@ApiParam(value = "쿠폰명") @PathVariable("couponName") String couponName
    , @ApiParam(value = "시작 일자") @PathVariable("frDt") String frDt, @ApiParam(value = "종료 일자") @PathVariable("toDt") String toDt
    , @ApiParam(value = "페이지") @PathVariable("page") int page) {

        if(couponName.equals("all")) {
            couponName = "";
        }

        Page<CouponDto> result = couponService.getCoupon(couponName, frDt, toDt, page);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/coupon/download/{page}")
    @Operation(summary = "쿠폰 다운 조회", description = "쿠폰 다운을 조회합니다")
    public ResponseEntity<ApiResponse<Object>> getCouponDownload(@PathVariable("page") int page) {
        Page<CouponDto> result = couponService.getCouponDownload(page);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @PostMapping("v1/admin/coupon")
    @Operation(summary = "쿠폰 추가", description = "쿠폰를 추가합니다")
    public ResponseEntity<ApiResponse<Object>> postUser(@ApiParam(value = "쿠폰") @RequestBody CouponDto couponDto) {
        couponService.postCoupon(couponDto);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @PutMapping("v1/admin/coupon")
    @Operation(summary = "쿠폰 수정", description = "쿠폰를 수정합니다")
    public ResponseEntity<ApiResponse<Object>> putUser(@ApiParam(value = "쿠폰") @RequestBody CouponDto couponDto) {
        couponService.putCoupon(couponDto);

        ApiResponse ar = ApiResponse.builder()
                .result("수정 성공")
                .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @DeleteMapping("v1/admin/coupon")
    @Operation(summary = "쿠폰 삭제", description = "쿠폰을 삭제합니다")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@ApiParam(value = "쿠폰") @RequestBody CouponDto couponDto) {
        couponService.deleteCoupon(couponDto);

        ApiResponse ar = ApiResponse.builder()
                .result("삭제 성공")
                .resultCode(SuccessCode.DELETE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }


}
