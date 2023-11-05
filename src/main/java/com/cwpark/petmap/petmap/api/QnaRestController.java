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
public class QnaRestController {

    private final UserQnaService userQnaService;

    @Autowired
    public QnaRestController(UserQnaService userQnaService, UserService userService) {
        this.userQnaService = userQnaService;
    }

    @GetMapping("v1/admin/userqna/{page}")
    @Operation(summary = "사용자 QnA 조회", description = "사용자 QnA를 조회합니다")
    public ResponseEntity<ApiResponse<Object>> getUserQna(@ApiParam(value = "페이지") @PathVariable("page") int page) {
        Page<UserQnaDto> result =  userQnaService.findByAll(page);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @PutMapping("v1/admin/userqna")
    @Operation(summary = "사용자 Qna 수정", description = "사용자 Qna를 수정합니다")
    public ResponseEntity<ApiResponse<Object>> putUserQna(@ApiParam(value = "QnA") @RequestBody UserQnaDto userQnaDto) {
        userQnaService.putAdminUserQna(userQnaDto);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }
}
