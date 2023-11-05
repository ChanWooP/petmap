package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.UserDto;
import com.cwpark.petmap.petmap.data.dto.UserPointHisDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.UserPointHisService;
import com.cwpark.petmap.petmap.service.UserService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserPointHisRestController {

    private final UserPointHisService userPointHisService;
    private final UserService userService;

    @Autowired
    public UserPointHisRestController(UserPointHisService userPointHisService, UserService userService) {
        this.userPointHisService = userPointHisService;
        this.userService = userService;
    }

    @GetMapping("v1/userpointhis/{userid}/{page}")
    @Operation(summary = "포인트 History 조회", description = "포인트 History를 조회합니다")
    public ResponseEntity<ApiResponse<Object>> getUserPointHis(@ApiParam(value = "사용자") @PathVariable("userid") String userId, @ApiParam(value = "페이지") @PathVariable("page") int page) {
        UserDto userDto = userService.getUser(userId, "id");
        Page<UserPointHisDto> userPointHisDtoPage = userPointHisService.findByUser(userDto, page);
        Map<String, Object> result = new HashMap<>();
        result.put("user", userDto);
        result.put("userPointHis", userPointHisDtoPage);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }
}
