package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.*;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SelOrderSaleRestController {
    private final SelOrderItemService selOrderItemService;
    private final SelOrderSaleService selOrderSaleService;

    @Autowired
    public SelOrderSaleRestController(SelOrderItemService selOrderItemService, SelOrderSaleService selOrderSaleService) {
        this.selOrderItemService = selOrderItemService;
        this.selOrderSaleService = selOrderSaleService;
    }

    @GetMapping("v1/sel/sale/{store}/{frDt}/{toDt}")
    public ResponseEntity<ApiResponse<Object>> getSale(@PathVariable("store") String store, @PathVariable("frDt") String frDt, @PathVariable("toDt") String toDt) throws Exception{
        Map<String, Object> result = new HashMap<>();

        result.put("month", selOrderSaleService.findBySaleMonth(store, frDt, toDt, 0));
        result.put("day", selOrderSaleService.getListSelOrderSale(store, frDt, toDt, 0));
        result.put("item", selOrderItemService.findByItemGroup(store, frDt, toDt, 0));

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/sel/sale/month/{store}/{frDt}/{toDt}/{page}")
    public ResponseEntity<ApiResponse<Object>> getMonth(@PathVariable("store") String store, @PathVariable("frDt") String frDt, @PathVariable("toDt") String toDt, @PathVariable("page") int page) throws Exception{

        ApiResponse ar = ApiResponse.builder()
                .result(selOrderSaleService.findBySaleMonth(store, frDt, toDt, page))
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/sel/sale/day/{store}/{frDt}/{toDt}/{page}")
    public ResponseEntity<ApiResponse<Object>> getDay(@PathVariable("store") String store, @PathVariable("frDt") String frDt, @PathVariable("toDt") String toDt, @PathVariable("page") int page) throws Exception{

        ApiResponse ar = ApiResponse.builder()
                .result(selOrderSaleService.getListSelOrderSale(store, frDt, toDt, page))
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }
}
