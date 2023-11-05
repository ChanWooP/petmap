package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.UserCartDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.UserCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserCartRestController {
    private final UserCartService userCartService;

    @Autowired
    public UserCartRestController(UserCartService userCartService) {
        this.userCartService = userCartService;
    }

    @PostMapping("v1/usercart")
    public ResponseEntity<ApiResponse<Object>> postUserCart(@RequestBody UserCartDto userCartDto) throws Exception{

        userCartService.postUserCart(userCartDto);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @PutMapping("v1/usercart")
    public ResponseEntity<ApiResponse<Object>> putUserCart(@RequestBody UserCartDto userCartDto) throws Exception{

        userCartService.putUserCart(userCartDto);

        ApiResponse ar = ApiResponse.builder()
                .result("수정 성공")
                .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @DeleteMapping("v1/usercart")
    public ResponseEntity<ApiResponse<Object>> deleteUserCart(@RequestBody UserCartDto userCartDto) throws Exception{

        userCartService.deleteUserCart(userCartDto);

        ApiResponse ar = ApiResponse.builder()
                .result("삭제 성공")
                .resultCode(SuccessCode.DELETE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/usercart/{userId}")
    public ResponseEntity<ApiResponse<Object>> getUserCart(@PathVariable("userId") String userId) {
        ApiResponse ar = ApiResponse.builder()
                .result(userCartService.findByUserOrderByItem(userId))
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }
}
