package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.UserItemQnaDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.UseritemQnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserItemQnaRestController {
    private final UseritemQnaService useritemQnaService;

    @Autowired
    public UserItemQnaRestController(UseritemQnaService useritemQnaService) {
        this.useritemQnaService = useritemQnaService;
    }

    @PostMapping("v1/useritemqna")
    public ResponseEntity<ApiResponse<Object>> postUserItemQna(@RequestBody UserItemQnaDto userItemQnaDto) {
        useritemQnaService.postUserItemQna(userItemQnaDto);

        ApiResponse ar = ApiResponse.builder()
                .result("추가 성공")
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @PutMapping("v1/useritemqna")
    public ResponseEntity<ApiResponse<Object>> putUserItemQna(@RequestBody UserItemQnaDto userItemQnaDto) {
        useritemQnaService.putUserItemQna(userItemQnaDto);

        ApiResponse ar = ApiResponse.builder()
                .result("수정 성공")
                .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @DeleteMapping("v1/useritemqna")
    public ResponseEntity<ApiResponse<Object>> deleteUserItemQna(@RequestBody UserItemQnaDto userItemQnaDto) {
        useritemQnaService.deleteUserItemQna(userItemQnaDto);

        ApiResponse ar = ApiResponse.builder()
                .result("삭제 성공")
                .resultCode(SuccessCode.DELETE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/useritemqna/list/{store}/{item}/{page}")
    public ResponseEntity<ApiResponse<Object>> getUserItemQna(@PathVariable("store") String store, @PathVariable("item") String item, @PathVariable("page") int page){
        Page<UserItemQnaDto> result = useritemQnaService.getUserItemQna(store, item, page);
        ApiResponse ar = null;

        if(result != null) {
            ar = ApiResponse.builder()
                    .result(result)
                    .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                    .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                    .build();
        } else {
            ar = ApiResponse.builder()
                    .result("NoData")
                    .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                    .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                    .build();
        }

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

}
