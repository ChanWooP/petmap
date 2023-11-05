package com.cwpark.petmap.petmap.api;

import com.cwpark.petmap.petmap.data.dto.SelOrderDto;
import com.cwpark.petmap.petmap.data.dto.SelOrderItemDto;
import com.cwpark.petmap.petmap.data.dto.UserOrderDto;
import com.cwpark.petmap.petmap.data.enums.SuccessCode;
import com.cwpark.petmap.petmap.data.response.ApiResponse;
import com.cwpark.petmap.petmap.service.SelOrderItemService;
import com.cwpark.petmap.petmap.service.SelOrderService;
import com.cwpark.petmap.petmap.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserOrderRestController {
    private final UserOrderService userOrderService;
    private final SelOrderService selOrderService;
    private final SelOrderItemService selOrderItemService;

    @Autowired
    public UserOrderRestController(UserOrderService userOrderService, SelOrderService selOrderService, SelOrderItemService selOrderItemService) {
        this.userOrderService = userOrderService;
        this.selOrderService = selOrderService;
        this.selOrderItemService = selOrderItemService;
    }

    @GetMapping("v1/user/order/{user}/{frDt}/{toDt}/{page}")
    public ResponseEntity<ApiResponse<Object>> getUserOrder(@PathVariable("user") String user, @PathVariable("frDt") String frDt, @PathVariable("toDt") String toDt, @PathVariable("page") int page) throws Exception{
        Map<String, Object> result = new HashMap<>();
        Map<String, List<SelOrderDto>> selOrderList = new HashMap<>();
        Map<String, List<SelOrderItemDto>> itemList = new HashMap<>();

        Page<UserOrderDto> userOrderDtoPage = userOrderService.getListUserOrder(user, frDt, toDt, page);

        for(UserOrderDto u : userOrderDtoPage.getContent()) {
            List<SelOrderDto> selOrder = selOrderService.findByOrdDtAndUserOrder(u.getOrdDt(), u);
            selOrderList.put("z"+ u.getUser() + u.getOrdDt() + u.getOrdId(), selOrder);
        }

        Iterator<String> keys = selOrderList.keySet().iterator();
        while(keys.hasNext()){
            List<SelOrderDto> selOrderDto = selOrderList.get(keys.next());
            for(SelOrderDto s : selOrderDto) {
                List<SelOrderItemDto> item = selOrderItemService.findBySelOrder(s.getUser().split("/")[0], s.getOrdDt(), s.getOrdId());
                itemList.put("z" + s.getUserOrderDto().getUser() + s.getUserOrderDto().getOrdDt() + s.getUserOrderDto().getOrdId() + s.getUser().split("/")[0] + s.getOrdDt() + s.getOrdId(), item);
            }
        }

        result.put("userOrder", userOrderDtoPage);
        result.put("selOrder", selOrderList);
        result.put("item", itemList);

        ApiResponse ar = ApiResponse.builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

}
