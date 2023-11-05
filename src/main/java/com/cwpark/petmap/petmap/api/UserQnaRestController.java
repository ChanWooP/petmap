package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.UserQnaDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.UserQnaService;
import com.cwpark.petmap.petmap.service.UserService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserQnaRestController {

    private final UserQnaService userQnaService;

    @Autowired
    public UserQnaRestController(UserQnaService userQnaService, UserService userService) {
        this.userQnaService = userQnaService;
    }

    @GetMapping("v1/userqna/{userid}/{page}")
    @Operation(summary = "사용자 QnA 조회", description = "사용자 QnA를 조회합니다")
    public ResponseEntity<ApiResponse<Object>> getUserQna(@ApiParam(value = "사용자") @PathVariable("userid") String userId, @ApiParam(value = "페이지") @PathVariable("page") int page) {
        Page<UserQnaDto> result = userQnaService.findByUser(userId, page);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @PostMapping("v1/userqna")
    @Operation(summary = "사용자 Qna 추가", description = "사용자 Qna를 추가합니다")
    public ResponseEntity<ApiResponse<Object>> postUserQna(@ApiParam(value = "QnA") @RequestBody UserQnaDto userQnaDto) {
        userQnaService.postUserQna(userQnaDto);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @PutMapping("v1/userqna")
    @Operation(summary = "사용자 Qna 수정", description = "사용자 Qna를 수정합니다")
    public ResponseEntity<ApiResponse<Object>> putUserQna(@ApiParam(value = "QnA") @RequestBody UserQnaDto userQnaDto) {
        userQnaService.putUserQna(userQnaDto);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }
}
