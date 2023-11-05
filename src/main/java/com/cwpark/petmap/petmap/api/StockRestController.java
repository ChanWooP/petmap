package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.dto.StockDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.ItemService;
import com.cwpark.petmap.petmap.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StockRestController {
    private final StockService stockService;
    private final ItemService itemService;

    @Autowired
    public StockRestController(StockService stockService, ItemService itemService) {
        this.stockService = stockService;
        this.itemService = itemService;
    }

    @PostMapping("v1/sel/stock")
    public ResponseEntity<ApiResponse<Object>> postItem(@RequestBody StockDto stockDto) throws IOException {
        stockService.postStock(stockDto);
        itemService.putItemStock(stockDto);

        ApiResponse ar = ApiResponse.builder()
                .result("저장 성공")
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @GetMapping("v1/sel/stock/{user}/{item}/{frDt}/{toDt}/{page}")
    public ResponseEntity<ApiResponse<Object>> itemCategory(@PathVariable("user") String user, @PathVariable("item") String item
            , @PathVariable("frDt") String frDt, @PathVariable("toDt") String toDt, @PathVariable("page") int page){
        Page<StockDto> stockPage = stockService.getStock(user, item, frDt, toDt, page);
        ItemDto itemDto = itemService.getItemOne(user, item);
        Map<String, Object> result = new HashMap<>();
        result.put("stock", stockPage);
        result.put("item", itemDto);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

}
