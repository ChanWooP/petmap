package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.SelOrderItemDao;
import com.cwpark.petmap.petmap.data.dao.SelOrderSaleDao;
import com.cwpark.petmap.petmap.data.domain.*;
import com.cwpark.petmap.petmap.data.dto.SelOrderItemDto;
import com.cwpark.petmap.petmap.data.dto.SelOrderSaleDto;
import com.cwpark.petmap.petmap.data.dto.SelOrderSaleGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class SelOrderSaleService {
    private final SelOrderSaleDao selOrderSaleDao;

    @Autowired
    public SelOrderSaleService(SelOrderSaleDao selOrderSaleDao) {
        this.selOrderSaleDao = selOrderSaleDao;
    }

    public void postSelOrderSale(SelOrderSaleDto selOrderSaleDto) {
        selOrderSaleDao.saveSelOrderSale(SelOrderSale.builder()
                        .selOrder(SelOrder.builder()
                                .user(User.builder().userId(selOrderSaleDto.getSelOrderDto().getUser()).build())
                                .ordDt(selOrderSaleDto.getSelOrderDto().getOrdDt())
                                .ordId(selOrderSaleDto.getSelOrderDto().getOrdId())
                                .userOrder(UserOrder.builder()
                                        .user(User.builder().userId(selOrderSaleDto.getSelOrderDto().getUserOrderDto().getUser()).build())
                                        .ordDt(selOrderSaleDto.getSelOrderDto().getUserOrderDto().getOrdDt())
                                        .ordId(selOrderSaleDto.getSelOrderDto().getUserOrderDto().getOrdId())
                                        .ordAddress(selOrderSaleDto.getSelOrderDto().getUserOrderDto().getOrdAddress())
                                        .ordName(selOrderSaleDto.getSelOrderDto().getUserOrderDto().getOrdName())
                                        .ordPhone(selOrderSaleDto.getSelOrderDto().getUserOrderDto().getOrdPhone())
                                        .ordTotAmt(selOrderSaleDto.getSelOrderDto().getUserOrderDto().getOrdTotAmt())
                                        .ordCouDc(selOrderSaleDto.getSelOrderDto().getUserOrderDto().getOrdCouDc())
                                        .ordPoiDc(selOrderSaleDto.getSelOrderDto().getUserOrderDto().getOrdPoiDc())
                                        .ordDelAmt(selOrderSaleDto.getSelOrderDto().getUserOrderDto().getOrdDelAmt())
                                        .ordNetAmt(selOrderSaleDto.getSelOrderDto().getUserOrderDto().getOrdNetAmt())
                                        .build())
                                .ordStatus(selOrderSaleDto.getSelOrderDto().getOrdStatus())
                                .ordInvoice(selOrderSaleDto.getSelOrderDto().getOrdInvoice())
                                .ordTotAmt(selOrderSaleDto.getSelOrderDto().getOrdTotAmt())
                                .ordDelAmt(selOrderSaleDto.getSelOrderDto().getOrdDelAmt())
                                .build())
                        .saleCnt(selOrderSaleDto.getSaleCnt())
                        .saleAmt(selOrderSaleDto.getSaleAmt())
                .build());
    }

    public void deleteSelOrderSale(SelOrderSaleDto selOrderSaleDto) {
        selOrderSaleDao.deleteSelOrderSale(SelOrderSale.builder()
                .selOrder(SelOrder.builder()
                        .user(User.builder().userId(selOrderSaleDto.getSelOrderDto().getUser()).build())
                        .ordDt(selOrderSaleDto.getSelOrderDto().getOrdDt())
                        .ordId(selOrderSaleDto.getSelOrderDto().getOrdId())
                        .build())
                .build());
    }

    public SelOrderSaleDto getOneSelOrderSale(String user, String ordDt, Long ordId, String store, String itemId) {
        return selOrderSaleDao.getOneSelOrderSale(user, ordDt, ordId);
    }

    public Page<SelOrderSaleDto> getListSelOrderSale(String user, String frDt, String toDt, int page) {
        return selOrderSaleDao.getListSelOrderSale(user, frDt, toDt, page);
    }

    public Page<SelOrderSaleGroupDto> findBySaleMonth(String store, String frDt, String toDt, int page) {
        return selOrderSaleDao.findBySaleMonth(store, frDt, toDt, page);
    }

}
