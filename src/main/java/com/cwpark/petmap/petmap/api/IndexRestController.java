package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.CategoryDto;
import com.cwpark.petmap.petmap.data.dto.NotifiDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.CategoryService;
import com.cwpark.petmap.petmap.service.NotifiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class IndexRestController {
    private final NotifiService notifiService;
    private final CategoryService categoryService;

    @Autowired
    public IndexRestController(NotifiService notifiService, CategoryService categoryService) {
        this.notifiService = notifiService;
        this.categoryService = categoryService;
    }

    @GetMapping("v1/index")
    public ResponseEntity<ApiResponse<Object>> getIndex() {
        Map<String, Object> result = new HashMap<>();

        List<NotifiDto> notifi = notifiService.getNotifiEvent();
        List<CategoryDto> category = categoryService.getCategory(0L, 0);

        result.put("notifi", notifi);
        result.put("category", category);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

}
