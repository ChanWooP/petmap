package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.*;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SelItemReviewRestController {

    private final SelItemReviewService selItemReviewService;
    private final SelOrderItemService selOrderItemService;
    private final ItemService itemService;

    @Autowired
    public SelItemReviewRestController(SelItemReviewService selItemReviewService, SelOrderItemService selOrderItemService, ItemService itemService) {
        this.selItemReviewService = selItemReviewService;
        this.selOrderItemService = selOrderItemService;
        this.itemService = itemService;
    }

    @PostMapping("v1/itemreview")
    public ResponseEntity<ApiResponse<Object>> postSelItemReview(@RequestBody SelItemReviewDto selItemReviewDto) throws Exception{
        SelOrderItemDto selOrderItemDto = selOrderItemService.getOneSelOrderItem(selItemReviewDto.getItem().getUser(), selItemReviewDto.getOrdDt(), selItemReviewDto.getOrdId(), selItemReviewDto.getItem().getUser(), selItemReviewDto.getItem().getSelItemId());
        ItemDto itemDto = itemService.getItemOne(selItemReviewDto.getItem().getUser(), selItemReviewDto.getItem().getSelItemId());
        selOrderItemDto.setItemReviewYn("Y");
        itemDto.setSelStockCount(0);
        itemDto.setSelHeartCount(0);
        itemDto.setSelSaleCount(0);
        itemDto.setSelStarPointCnt(1);
        itemDto.setSelStarPoint(selItemReviewDto.getReviewStarPoint());

        selItemReviewService.postSelItemReview(selItemReviewDto);
        selOrderItemService.postSelOrderItem(selOrderItemDto);
        itemService.putItem(itemDto, null);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/itemreview/{store}/{item}/{page}")
    public ResponseEntity<ApiResponse<Object>> getSelItemReview(@PathVariable("store") String store, @PathVariable("item") String item, @PathVariable("page") int page) {
        Page<SelItemReviewDto> result = selItemReviewService.findByItem(store, item, page);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }



}
