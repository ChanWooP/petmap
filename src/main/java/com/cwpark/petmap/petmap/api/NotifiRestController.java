package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.NotifiDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.NotifiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NotifiRestController {
    private final NotifiService notifiService;

    @Autowired
    public NotifiRestController(NotifiService notifiService) {
        this.notifiService = notifiService;
    }

    @PostMapping("v1/admin/notifi")
    public ResponseEntity<ApiResponse<Object>> postNotifi( @RequestPart(value = "json") NotifiDto notifiDto,
                                                           @RequestPart(value = "files",required = false) List<MultipartFile> file) throws IOException {
        notifiService.postNotifi(notifiDto, file);

        ApiResponse ar = ApiResponse.builder()
                .result("추가 성공")
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @PutMapping("v1/admin/notifi")
    public ResponseEntity<ApiResponse<Object>> putNotifi(@RequestPart(value = "json") NotifiDto notifiDto,
                                                         @RequestPart(value = "files",required = false) List<MultipartFile> file) throws IOException {
        notifiService.postNotifi(notifiDto, file);

        ApiResponse ar = ApiResponse.builder()
                .result("수정 성공")
                .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @DeleteMapping("v1/admin/notifi")
    public ResponseEntity<ApiResponse<Object>> deleteNotifi(@RequestBody NotifiDto notifiDto) {
        notifiService.deleteNotifi(notifiDto);

        ApiResponse ar = ApiResponse.builder()
                .result("삭제 성공")
                .resultCode(SuccessCode.DELETE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/notifi/{page}")
    public ResponseEntity<ApiResponse<Object>> getCategory(@PathVariable("page") int page){
        Page<NotifiDto> result = notifiService.getNotifi(page);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }


}
