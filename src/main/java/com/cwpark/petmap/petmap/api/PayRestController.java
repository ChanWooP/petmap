package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.config.exception.NoSuchDataException;
import com.cwpark.petmap.petmap.data.dto.*;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PayRestController {
    private final UserCouponService userCouponService;
    private final UserService userService;
    private final UserPointHisService userPointHisService;
    private final UserOrderService userOrderService;
    private final SelOrderService selOrderService;
    private final SelOrderItemService selOrderItemService;

    @Autowired
    public PayRestController(UserCouponService userCouponService, UserService userService, UserPointHisService userPointHisService, UserOrderService userOrderService, SelOrderService selOrderService, SelOrderItemService selOrderItemService) {
        this.userCouponService = userCouponService;
        this.userService = userService;
        this.userPointHisService = userPointHisService;
        this.userOrderService = userOrderService;
        this.selOrderService = selOrderService;
        this.selOrderItemService = selOrderItemService;
    }

    @PostMapping("v1/pay")
    public ResponseEntity<ApiResponse<Object>> getUserItemQna(@RequestBody PayDto payDto) throws Exception{
        UserDto user = UserDto.builder().userId(payDto.getUser().getUser()).build();

        UserCouponDto coupon = UserCouponDto.builder()
                .userDto(user)
                .couponDto(CouponDto.builder().couponCode(payDto.getDc().getCouponCode()).build())
                .build();

        int point = payDto.getDc().getPoint();

        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = simpleDateFormat.format(nowDate);

        UserOrderDto userOrderDto = UserOrderDto.builder()
                .user(user.getUserId())
                .ordDt(today)
                .ordAddress(payDto.getAddress().getAddress())
                .ordName(payDto.getAddress().getName())
                .ordPhone(payDto.getAddress().getPhone())
                .ordTotAmt(payDto.getPay().getItemPrice())
                .ordCouDc(payDto.getPay().getCoupon())
                .ordPoiDc(payDto.getPay().getPoint())
                .ordDelAmt(payDto.getPay().getDeliPrice())
                .ordNetAmt(payDto.getPay().getTotPrice())
                .build();

        List<SelOrderDto> selOrderDtoList = new ArrayList<>();
        SelOrderDto selOrderDtoTemp = null;
        List<SelOrderItemDto> selOrderItemDtoList = new ArrayList<>();

        for(PayItemDto item : payDto.getItem()) {
            if(selOrderDtoTemp == null || !selOrderDtoTemp.getUser().equals(item.getStore().split("/")[0])) {
                selOrderDtoTemp = SelOrderDto.builder()
                        .user(item.getStore().split("/")[0])
                        .ordDt(today)
                        .userOrderDto(userOrderDto)
                        .ordStatus("W")
                        .ordDelAmt(item.getItemDeli())
                        .ordTotAmt(0)
                        .build();

                selOrderDtoList.add(selOrderDtoTemp);
            }

            selOrderDtoList.get(selOrderDtoList.size() - 1).setOrdTotAmt(
                    selOrderDtoList.get(selOrderDtoList.size() - 1).getOrdTotAmt()
                    + (item.getItemCnt() * item.getItemPrice())
            );

            selOrderItemDtoList.add(SelOrderItemDto.builder()
                            .selOrderDto(selOrderDtoTemp)
                            .itemDto(ItemDto.builder()
                                    .user(item.getStore().split("/")[0])
                                    .selItemId(item.getItemId())
                                    .build())
                            .ordCnt(item.getItemCnt())
                            .ordAmt(item.getItemPrice() * item.getItemCnt())
                    .build());
        }

        if(coupon.getCouponDto().getCouponCode() != null) {
            if(!userCouponService.useUserCoupon(coupon, "-")) {
                payRollback("coupon", user, coupon, point);
            }
        }

        if(point > 0) {
            if(!useUserPoint(user, point)) {
                payRollback("point", user, coupon, point);
            }
        }

        userOrderService.postUserOrder(userOrderDto);

        for(SelOrderDto selOrder : selOrderDtoList) {
            selOrderService.postSelOrder(selOrder);
        }

        for(SelOrderItemDto selOrderItem : selOrderItemDtoList) {
            selOrderItemService.postSelOrderItem(selOrderItem);
        }

        ApiResponse ar = ApiResponse.builder()
                .result("저장 완료")
                .resultCode(SuccessCode.INSERT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    private boolean useUserPoint(UserDto userDto, int point) {
        if(!userService.useUserPoint(userDto.getUserId(), point, "-")) {
            return false;
        } else {
            userPointHisService.postUserPointHis(UserPointHisDto.builder()
                    .userDto(userDto)
                    .pointNum(-point)
                    .pointReason("buyItem")
                    .pointExpln("상품 구매")
                    .build());

            return true;
        }
    }

    private void payRollback(String str, UserDto userDto, UserCouponDto userCouponDto, int point) throws Exception {
        if(str.equals("coupon")) {
            throw new NoSuchDataException("쿠폰이 존재하지 않습니다");
        } else if(str.equals("point")) {
            userCouponRollback(userCouponDto);
            throw new NoSuchDataException("포인트가 부족합니다");
        }
    }

    private void userCouponRollback(UserCouponDto userCouponDto) {
        userCouponService.useUserCoupon(userCouponDto, "+");
    }

    private void userPointRollback(UserDto userDto, int point) {
        userService.useUserPoint(userDto.getUserId(), point, "+");
    }

}
