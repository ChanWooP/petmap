package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ItemRestController {
    private final ItemService itemService;

    @Autowired
    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("v1/sel/item")
    public ResponseEntity<ApiResponse<Object>> postItem(@RequestPart(value = "json") ItemDto itemDto,
                                                        @RequestPart(value = "files",required = false) List<MultipartFile> files) throws IOException {
        itemService.postItem(itemDto, files);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @PutMapping("v1/sel/item")
    public ResponseEntity<ApiResponse<Object>> putItem(@RequestPart(value = "json") ItemDto itemDto,
                                                       @RequestPart(value = "files",required = false) List<MultipartFile> files) throws IOException {
        itemService.putItem(itemDto, files);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @DeleteMapping("v1/sel/item")
    public ResponseEntity<ApiResponse<Object>> deleteCategory(@RequestBody ItemDto itemDto) {
        itemService.deleteItem(itemDto);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.DELETE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/sel/item/{itemSearch}/{page}/{sort}/{user}")
    public ResponseEntity<ApiResponse<Object>> getCouponDownload(@PathVariable("itemSearch") String itemSearch, @PathVariable("page") int page, @PathVariable("sort") String sort, @PathVariable("user") String user) {
        Page<ItemDto> result = itemService.getItem(itemSearch, page, sort, user);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/item/{type}/{search}/{page}/{sort}")
    public ResponseEntity<ApiResponse<Object>> getItemSerach(@PathVariable("type") String type, @PathVariable("search") String search, @PathVariable("page") int page, @PathVariable("sort") String sort) {
        Page<ItemDto> result = itemService.getItemSearch(type, search, page, sort);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/sel/item/all/{user}")
    public ResponseEntity<ApiResponse<Object>> getItemAll(@PathVariable("user") String user) {
        List<ItemDto> result = itemService.getItemAll(user);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/item/one/{user}/{selItemId}")
    public ResponseEntity<ApiResponse<Object>> getCouponDownload(@PathVariable("user") String user, @PathVariable("selItemId") String selItemId) {
        ItemDto result = itemService.getItemOne(user, selItemId);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/item/list/{list}")
    public ResponseEntity<ApiResponse<Object>> getItemList(@PathVariable("list") String str) {
        String[] strArray = str.split("&");
        String[] storeArray = strArray[0].split("!");
        String[] itemArray = strArray[1].split("!");
        String[] cntArray = strArray[2].split("!");

        Map<String, List<ItemDto>> result = itemService.getItemList(storeArray, itemArray, cntArray);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

}
