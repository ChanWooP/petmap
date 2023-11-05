package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.SelOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SelOrderItemRestController {
    private final SelOrderItemService selOrderItemService;

    @Autowired
    public SelOrderItemRestController(SelOrderItemService selOrderItemService) {
        this.selOrderItemService = selOrderItemService;

    }

    @GetMapping("v1/sel/orderitem/{store}/{frDt}/{toDt}/{page}")
    public ResponseEntity<ApiResponse<Object>> getItem(@PathVariable("store") String store, @PathVariable("frDt") String frDt, @PathVariable("toDt") String toDt, @PathVariable("page") int page) throws Exception{

        ApiResponse ar = ApiResponse.builder()
                .result(selOrderItemService.findByItemGroup(store, frDt, toDt, 0))
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }
}
