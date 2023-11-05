package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.UserAddressDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserAddressRestController {
    private final UserAddressService userAddressService;

    @Autowired
    public UserAddressRestController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @PostMapping("v1/user/address")
    public ResponseEntity<ApiResponse<Object>> postUserAddress(@RequestBody UserAddressDto userAddressDto) throws Exception{
        userAddressService.postAddressUser(userAddressDto);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @DeleteMapping("v1/user/address")
    public ResponseEntity<ApiResponse<Object>> deleteUserAddress(@RequestBody UserAddressDto userAddressDto) {
        userAddressService.deleteAddressUser(userAddressDto);

        ApiResponse ar = ApiResponse.builder()
                .result("삭제 성공")
                .resultCode(SuccessCode.DELETE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/user/address/{user}")
    public ResponseEntity<ApiResponse<Object>> getUserAddress(@PathVariable("user") String user){
        List<UserAddressDto> result = userAddressService.getAddressUser(user);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

}
