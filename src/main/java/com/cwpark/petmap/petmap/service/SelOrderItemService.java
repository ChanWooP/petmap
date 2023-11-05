package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.SelOrderDao;
import com.cwpark.petmap.petmap.data.dao.SelOrderItemDao;
import com.cwpark.petmap.petmap.data.domain.*;
import com.cwpark.petmap.petmap.data.dto.SelOrderDto;
import com.cwpark.petmap.petmap.data.dto.SelOrderItemDto;
import com.cwpark.petmap.petmap.data.dto.SelOrderItemGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SelOrderItemService {
    private final SelOrderItemDao selOrderItemDao;

    @Autowired
    public SelOrderItemService(SelOrderItemDao selOrderItemDao) {
        this.selOrderItemDao = selOrderItemDao;
    }

    public void postSelOrderItem(SelOrderItemDto selOrderItemDto) {
        selOrderItemDao.saveSelOrderItem(SelOrderItem.builder()
                        .selOrder(SelOrder.builder()
                                .user(User.builder().userId(selOrderItemDto.getSelOrderDto().getUser()).build())
                                .ordDt(selOrderItemDto.getSelOrderDto().getOrdDt())
                                .ordId(selOrderItemDto.getSelOrderDto().getOrdId())
                                .userOrder(UserOrder.builder()
                                        .user(User.builder().userId(selOrderItemDto.getSelOrderDto().getUserOrderDto().getUser()).build())
                                        .ordDt(selOrderItemDto.getSelOrderDto().getUserOrderDto().getOrdDt())
                                        .ordId(selOrderItemDto.getSelOrderDto().getUserOrderDto().getOrdId())
                                        .ordAddress(selOrderItemDto.getSelOrderDto().getUserOrderDto().getOrdAddress())
                                        .ordName(selOrderItemDto.getSelOrderDto().getUserOrderDto().getOrdName())
                                        .ordPhone(selOrderItemDto.getSelOrderDto().getUserOrderDto().getOrdPhone())
                                        .ordTotAmt(selOrderItemDto.getSelOrderDto().getUserOrderDto().getOrdTotAmt())
                                        .ordCouDc(selOrderItemDto.getSelOrderDto().getUserOrderDto().getOrdCouDc())
                                        .ordPoiDc(selOrderItemDto.getSelOrderDto().getUserOrderDto().getOrdPoiDc())
                                        .ordDelAmt(selOrderItemDto.getSelOrderDto().getUserOrderDto().getOrdDelAmt())
                                        .ordNetAmt(selOrderItemDto.getSelOrderDto().getUserOrderDto().getOrdNetAmt())
                                        .build())
                                .ordStatus(selOrderItemDto.getSelOrderDto().getOrdStatus())
                                .ordInvoice(selOrderItemDto.getSelOrderDto().getOrdInvoice())
                                .ordTotAmt(selOrderItemDto.getSelOrderDto().getOrdTotAmt())
                                .ordDelAmt(selOrderItemDto.getSelOrderDto().getOrdDelAmt())
                                .build())
                        .item(Item.builder()
                                .user(User.builder().userId(selOrderItemDto.getItemDto().getUser()).build())
                                .selItemId(selOrderItemDto.getItemDto().getSelItemId())
                                .build())
                        .ordCnt(selOrderItemDto.getOrdCnt())
                        .ordAmt(selOrderItemDto.getOrdAmt())
                        .itemReviewYn(selOrderItemDto.getItemReviewYn())
                .build());
    }

    public void deleteSelOrderItem(SelOrderItemDto selOrderItemDto) {
        selOrderItemDao.deleteSelOrderItem(SelOrderItem.builder()
                .selOrder(SelOrder.builder()
                        .user(User.builder().userId(selOrderItemDto.getSelOrderDto().getUser()).build())
                        .ordDt(selOrderItemDto.getSelOrderDto().getOrdDt())
                        .ordId(selOrderItemDto.getSelOrderDto().getOrdId())
                        .build())
                .item(Item.builder()
                        .user(User.builder().userId(selOrderItemDto.getItemDto().getUser()).build())
                        .selItemId(selOrderItemDto.getItemDto().getSelItemId())
                        .build())
                .build());
    }

    public SelOrderItemDto getOneSelOrderItem(String user, String ordDt, Long ordId, String store, String itemId) {
        return selOrderItemDao.getOneSelOrderItem(user, ordDt, ordId, store, itemId);
    }

    public Page<SelOrderItemDto> getListSelOrderItem(String user, String frDt, String toDt, int page) {
        return selOrderItemDao.getListSelOrderItem(user, frDt, toDt, page);
    }

    public List<SelOrderItemDto> findBySelOrder(String user, String ordDt, Long ordId) {
        return selOrderItemDao.findBySelOrder(user, ordDt, ordId);
    }

    public Page<SelOrderItemGroupDto> findByItemGroup(String store, String frDt, String toDt, int page) {
        return selOrderItemDao.findByItemGroup(store, frDt, toDt, page);
    }

}
