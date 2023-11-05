package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.SelOrderDao;
import com.cwpark.petmap.petmap.data.dao.UserOrderDao;
import com.cwpark.petmap.petmap.data.domain.SelOrder;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserOrder;
import com.cwpark.petmap.petmap.data.dto.SelOrderDto;
import com.cwpark.petmap.petmap.data.dto.UserOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SelOrderService {
    private final SelOrderDao selOrderDao;

    @Autowired
    public SelOrderService(SelOrderDao selOrderDao) {
        this.selOrderDao = selOrderDao;
    }

    public void postSelOrder(SelOrderDto selOrderDto) {
        Long id = selOrderDao.getSelOrderId(selOrderDto.getUser());
        selOrderDto.setOrdId(id);

        selOrderDao.saveSelOrder(SelOrder.builder()
                .user(User.builder().userId(selOrderDto.getUser()).build())
                .ordDt(selOrderDto.getOrdDt())
                .ordId(selOrderDto.getOrdId())
                .userOrder(UserOrder.builder()
                        .user(User.builder().userId(selOrderDto.getUserOrderDto().getUser()).build())
                        .ordDt(selOrderDto.getUserOrderDto().getOrdDt())
                        .ordId(selOrderDto.getUserOrderDto().getOrdId())
                        .ordAddress(selOrderDto.getUserOrderDto().getOrdAddress())
                        .ordName(selOrderDto.getUserOrderDto().getOrdName())
                        .ordPhone(selOrderDto.getUserOrderDto().getOrdPhone())
                        .ordTotAmt(selOrderDto.getUserOrderDto().getOrdTotAmt())
                        .ordCouDc(selOrderDto.getUserOrderDto().getOrdCouDc())
                        .ordPoiDc(selOrderDto.getUserOrderDto().getOrdPoiDc())
                        .ordDelAmt(selOrderDto.getUserOrderDto().getOrdDelAmt())
                        .ordNetAmt(selOrderDto.getUserOrderDto().getOrdNetAmt())
                        .build())
                .ordStatus(selOrderDto.getOrdStatus())
                .ordInvoice(selOrderDto.getOrdInvoice())
                .ordTotAmt(selOrderDto.getOrdTotAmt())
                .ordDelAmt(selOrderDto.getOrdDelAmt())
                .build());
    }

    public SelOrderDto putSelOrder(SelOrderDto selOrder) {
        SelOrderDto selOrderDto = selOrderDao.getOneSelOrder(selOrder.getUser(), selOrder.getOrdDt(), selOrder.getOrdId());

        if(selOrder.getOrdStatus() != null) {
            selOrderDto.setOrdStatus(selOrder.getOrdStatus());
        }

        if(selOrder.getOrdInvoice() != null) {
            selOrderDto.setOrdInvoice(selOrder.getOrdInvoice());
        }

        return selOrderDao.saveSelOrder(SelOrder.builder()
                .user(User.builder().userId(selOrderDto.getUser()).build())
                .ordDt(selOrderDto.getOrdDt())
                .ordId(selOrderDto.getOrdId())
                .userOrder(UserOrder.builder()
                        .user(User.builder().userId(selOrderDto.getUserOrderDto().getUser()).build())
                        .ordDt(selOrderDto.getUserOrderDto().getOrdDt())
                        .ordId(selOrderDto.getUserOrderDto().getOrdId())
                        .ordAddress(selOrderDto.getUserOrderDto().getOrdAddress())
                        .ordName(selOrderDto.getUserOrderDto().getOrdName())
                        .ordPhone(selOrderDto.getUserOrderDto().getOrdPhone())
                        .ordTotAmt(selOrderDto.getUserOrderDto().getOrdTotAmt())
                        .ordCouDc(selOrderDto.getUserOrderDto().getOrdCouDc())
                        .ordPoiDc(selOrderDto.getUserOrderDto().getOrdPoiDc())
                        .ordDelAmt(selOrderDto.getUserOrderDto().getOrdDelAmt())
                        .ordNetAmt(selOrderDto.getUserOrderDto().getOrdNetAmt())
                        .build())
                .ordStatus(selOrderDto.getOrdStatus())
                .ordInvoice(selOrderDto.getOrdInvoice())
                .ordTotAmt(selOrderDto.getOrdTotAmt())
                .ordDelAmt(selOrderDto.getOrdDelAmt())
                .build());
    }

    public void deleteSelOrder(SelOrderDto selOrderDto) {
        selOrderDao.deleteSelOrder(SelOrder.builder()
                .user(User.builder().userId(selOrderDto.getUser()).build())
                .ordDt(selOrderDto.getOrdDt())
                .ordId(selOrderDto.getOrdId())
                .build());
    }

    public SelOrderDto getOneSelOrder(String user, String ordDt, Long ordId) {
        return selOrderDao.getOneSelOrder(user, ordDt, ordId);
    }

    public Page<SelOrderDto> getListSelOrder(String user, String frDt, String toDt, int page) throws Exception {
        return selOrderDao.getListSelOrder(user, frDt, toDt, page);
    }

    public List<SelOrderDto> findByOrdDtAndUserOrder(String ordDt, UserOrderDto userOrderDto) {
        UserOrder userOrder = UserOrder.builder()
                .user(User.builder().userId(userOrderDto.getUser()).build())
                .ordDt(userOrderDto.getOrdDt())
                .ordId(userOrderDto.getOrdId())
                .build();

        return selOrderDao.findByOrdDtAndUserOrder(ordDt, userOrder);
    }

}
