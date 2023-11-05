package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.CategoryDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.CategoryService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryRestController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("v1/admin/category")
    @Operation(summary = "카테고리 추가", description = "카테고리를 추가합니다")
    public ResponseEntity<ApiResponse<Object>> postCategory(@ApiParam(value = "카테고리") @RequestPart(value = "json") CategoryDto categoryDto,
                                                        @ApiParam(value = "이미지") @RequestPart(value = "file",required = false) MultipartFile file) throws IOException {
        categoryService.saveCategory(categoryDto, file);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @PutMapping("v1/admin/category")
    public ResponseEntity<ApiResponse<Object>> putCategory(@ApiParam(value = "카테고리") @RequestPart(value = "json") CategoryDto categoryDto,
                                                            @ApiParam(value = "이미지") @RequestPart(value = "file",required = false) MultipartFile file) throws IOException {
        categoryService.saveCategory(categoryDto, file);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @DeleteMapping("v1/admin/category")
    public ResponseEntity<ApiResponse<Object>> deleteCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.deleteCategory(categoryDto);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.DELETE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/category/{parent}/{depth}")
    public ResponseEntity<ApiResponse<Object>> getCategory(@PathVariable("parent") Long parent, @PathVariable("depth") int depth){
        List<CategoryDto> result = categoryService.getCategory(parent, depth);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/category/item/{categoryId}")
    public ResponseEntity<ApiResponse<Object>> itemCategory(@PathVariable("categoryId") Long categoryId){
        String[] result = categoryService.itemCategory(categoryId);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

}
