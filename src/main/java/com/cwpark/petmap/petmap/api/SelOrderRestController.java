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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SelOrderRestController {
    private final UserOrderService userOrderService;
    private final SelOrderService selOrderService;
    private final SelOrderItemService selOrderItemService;
    private final SelOrderSaleService selOrderSaleService;
    private final UserService userService;
    private final UserPointHisService userPointHisService;
    private final ItemService itemService;
    private final StockService stockService;

    @Autowired
    public SelOrderRestController(UserOrderService userOrderService, SelOrderService selOrderService, SelOrderItemService selOrderItemService, SelOrderSaleService selOrderSaleService, UserService userService, UserPointHisService userPointHisService, ItemService itemService, StockService stockService) {
        this.userOrderService = userOrderService;
        this.selOrderService = selOrderService;
        this.selOrderItemService = selOrderItemService;
        this.selOrderSaleService = selOrderSaleService;
        this.userService = userService;
        this.userPointHisService = userPointHisService;
        this.itemService = itemService;
        this.stockService = stockService;
    }

    @GetMapping("v1/order/{store}/{frDt}/{toDt}/{page}")
    public ResponseEntity<ApiResponse<Object>> getUserItemQna(@PathVariable("store") String store, @PathVariable("frDt") String frDt, @PathVariable("toDt") String toDt, @PathVariable("page") int page) throws Exception{
        Map<String, Object> result = new HashMap<>();
        Map<String, List<SelOrderItemDto>> itemList = new HashMap<>();

        Page<SelOrderDto> selOrderDtoList = selOrderService.getListSelOrder(store, frDt, toDt, page);

        for(SelOrderDto s : selOrderDtoList.getContent()) {
            List<SelOrderItemDto> item = selOrderItemService.findBySelOrder(s.getUser(), s.getOrdDt(), s.getOrdId());
            itemList.put("z"+s.getUser()+s.getOrdDt()+s.getOrdId(), item);
        }

        result.put("order", selOrderDtoList);
        result.put("item", itemList);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    @PutMapping("v1/order")
    public ResponseEntity<ApiResponse<Object>> putSelOrder(@RequestBody SelOrderDto selOrderDto) throws Exception{
        SelOrderDto selOrder = selOrderService.putSelOrder(selOrderDto);

        if(selOrder.getOrdStatus().equals("C")) {
            UserOrderDto userOrder = selOrder.getUserOrderDto();
            int originTot = userOrder.getOrdTotAmt();
            int originCouDc = userOrder.getOrdCouDc();
            int originPoiDc = userOrder.getOrdPoiDc();
            int originDelAmt = userOrder.getOrdDelAmt();
            double newPercent = (double) originCouDc / (double) originTot;

            int newTot = originTot - selOrder.getOrdTotAmt();
            double newCouDc = newTot * newPercent;
            int newDelAmt = originDelAmt - selOrder.getOrdDelAmt();
            int newPoiDc = 0;

            userOrder.setOrdTotAmt(newTot);
            userOrder.setOrdCouDc((int) newCouDc);
            userOrder.setOrdDelAmt(newDelAmt);

            if (newTot == 0) {
                newPoiDc = 0;
            } else if ((newTot - (int) newCouDc) - originPoiDc < 0) {
                newPoiDc = originPoiDc - (newTot - (int) newCouDc);
            }

            userService.useUserPoint(userOrder.getUser(), originPoiDc, "+");
            userPointHisService.postUserPointHis(UserPointHisDto.builder()
                    .userDto(UserDto.builder().userId(userOrder.getUser()).build())
                    .pointNum(originPoiDc)
                    .pointReason("refund")
                    .pointExpln("환불")
                    .build());

            if (newTot != 0 && originPoiDc != newPoiDc) {
                userOrder.setOrdPoiDc(newPoiDc);
                userService.useUserPoint(userOrder.getUser(), newPoiDc, "-");
                userPointHisService.postUserPointHis(UserPointHisDto.builder()
                        .userDto(UserDto.builder().userId(userOrder.getUser()).build())
                        .pointNum(-newPoiDc)
                        .pointReason("buyItem")
                        .pointExpln("상품구매")
                        .build());
            } else if(newTot == 0) {
                userOrder.setOrdPoiDc(0);
            } else {
                userOrder.setOrdPoiDc(originPoiDc);
                userPointHisService.postUserPointHis(UserPointHisDto.builder()
                        .userDto(UserDto.builder().userId(userOrder.getUser()).build())
                        .pointNum(-originPoiDc)
                        .pointReason("buyItem")
                        .pointExpln("상품구매")
                        .build());
            }

            userOrder.setOrdNetAmt(newTot - (int)newCouDc + newDelAmt - userOrder.getOrdPoiDc());

            userOrderService.putUserOrder(userOrder);
        } else if(selOrder.getOrdStatus().equals("F")) {
            List<SelOrderItemDto> list = selOrderItemService.findBySelOrder(selOrder.getUser(), selOrder.getOrdDt(), selOrder.getOrdId());

            int cnt = 0;

            for(SelOrderItemDto s : list) {
                cnt += s.getOrdCnt();
                s.getItemDto().setSelSaleCount(1);
                s.getItemDto().setSelHeartCount(0);
                s.getItemDto().setSelStarPoint(0);
                s.getItemDto().setSelStockCount(0);
                itemService.putItem(s.getItemDto(), null);
            }

            selOrderSaleService.postSelOrderSale(SelOrderSaleDto.builder()
                            .selOrderDto(selOrder)
                            .saleAmt(selOrder.getOrdTotAmt())
                            .saleCnt(cnt)
                    .build());
        } else if(selOrder.getOrdStatus().equals("G")) {
            List<SelOrderItemDto> list = selOrderItemService.findBySelOrder(selOrder.getUser(), selOrder.getOrdDt(), selOrder.getOrdId());

            for(SelOrderItemDto s : list) {
                StockDto stock = StockDto.builder()
                        .itemDto(s.getItemDto())
                        .stockDt(s.getSelOrderDto().getOrdDt())
                        .stockQty(-s.getOrdCnt())
                        .stockExpln("상품 판매")
                        .build();

                stockService.postStock(stock);
                itemService.putItemStock(stock);
            }

        }

        ApiResponse ar = ApiResponse.builder()
                .result("수정 성공")
                .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

}
