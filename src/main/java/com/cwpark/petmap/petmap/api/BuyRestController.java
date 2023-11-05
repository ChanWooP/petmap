package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.dto.SelItemReviewDto;
import com.cwpark.petmap.petmap.data.dto.UserItemQnaDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.ItemService;
import com.cwpark.petmap.petmap.service.SelItemReviewService;
import com.cwpark.petmap.petmap.service.UseritemQnaService;
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
public class BuyRestController {
    private final UseritemQnaService useritemQnaService;
    private final ItemService itemService;
    private final SelItemReviewService selItemReviewService;

    @Autowired
    public BuyRestController(UseritemQnaService useritemQnaService, ItemService itemService, SelItemReviewService selItemReviewService) {
        this.useritemQnaService = useritemQnaService;
        this.itemService = itemService;
        this.selItemReviewService = selItemReviewService;
    }

    @GetMapping("v1/buy/{store}/{item}")
    public ResponseEntity<ApiResponse<Object>> getUserItemQna(@PathVariable("store") String store, @PathVariable("item") String item){
        Map<String, Object> result = new HashMap<>();
        ItemDto itemResult = itemService.getItemOne(store, item);
        Page<UserItemQnaDto> userItemQnaResult = useritemQnaService.getUserItemQna(store, item, 0);
        Page<SelItemReviewDto> selItemReviewResult = selItemReviewService.findByItem(store, item, 0);

        result.put("item", itemResult);
        result.put("useritemqna", userItemQnaResult);
        result.put("review", selItemReviewResult);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

}
