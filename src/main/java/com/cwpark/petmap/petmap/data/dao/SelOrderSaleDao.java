package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.data.classid.ItemClassId;
import com.cwpark.petmap.petmap.data.classid.SelOrderClassId;
import com.cwpark.petmap.petmap.data.classid.SelOrderItemClassId;
import com.cwpark.petmap.petmap.data.classid.SelOrderSaleClassId;
import com.cwpark.petmap.petmap.data.domain.SelOrderItem;
import com.cwpark.petmap.petmap.data.domain.SelOrderSale;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.dto.*;
import com.cwpark.petmap.petmap.data.persistence.SelOrderItemRepository;
import com.cwpark.petmap.petmap.data.persistence.SelOrderSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SelOrderSaleDao {
    private final SelOrderSaleRepository selOrderSaleRepository;

    @Autowired
    public SelOrderSaleDao(SelOrderSaleRepository selOrderSaleRepository) {
        this.selOrderSaleRepository = selOrderSaleRepository;
    }

    public void saveSelOrderSale(SelOrderSale selOrderSale) {
        selOrderSaleRepository.save(selOrderSale);
    }

    public void deleteSelOrderSale(SelOrderSale selOrderSale) {
        selOrderSaleRepository.delete(selOrderSale);
    }

    public SelOrderSaleDto getOneSelOrderSale(String user, String ordDt, Long ordId) {
        SelOrderClassId selOrderClassId = SelOrderClassId.builder()
                .user(user)
                .ordDt(ordDt)
                .ordId(ordId)
                .build();

        SelOrderSaleClassId selOrderSaleClassId = SelOrderSaleClassId.builder()
                .selOrder(selOrderClassId)
                .build();

        Optional<SelOrderSale> optional = selOrderSaleRepository.findById(selOrderSaleClassId);
        SelOrderSale selOrderSale = optional.orElse(null);

        if(selOrderSale != null) {
            return SelOrderSaleDto.builder()
                    .selOrderDto(SelOrderDto.builder()
                            .user(selOrderSale.getSelOrder().getUser().getUserId())
                            .ordDt(selOrderSale.getSelOrder().getOrdDt())
                            .ordId(selOrderSale.getSelOrder().getOrdId())
                            .ordStatus(selOrderSale.getSelOrder().getOrdStatus())
                            .ordInvoice(selOrderSale.getSelOrder().getOrdInvoice())
                            .ordTotAmt(selOrderSale.getSelOrder().getOrdTotAmt())
                            .ordDelAmt(selOrderSale.getSelOrder().getOrdDelAmt())
                            .build())
                    .saleCnt(selOrderSale.getSaleCnt())
                    .saleAmt(selOrderSale.getSaleAmt())
                    .build();
        } else {
            return null;
        }
    }

    public Page<SelOrderSaleDto> getListSelOrderSale(String user, String frDt, String toDt, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "regDate"));
        Page<SelOrderSale> result = selOrderSaleRepository.getListSelOrderSale(user, frDt, toDt, pageable);

        return result.map(s -> SelOrderSaleDto.builder()
                .selOrderDto(SelOrderDto.builder()
                        .user(s.getSelOrder().getUser().getUserId())
                        .ordDt(s.getSelOrder().getOrdDt())
                        .ordId(s.getSelOrder().getOrdId())
                        .ordStatus(s.getSelOrder().getOrdStatus())
                        .ordInvoice(s.getSelOrder().getOrdInvoice())
                        .ordTotAmt(s.getSelOrder().getOrdTotAmt())
                        .ordDelAmt(s.getSelOrder().getOrdDelAmt())
                        .build())
                .saleCnt(s.getSaleCnt())
                .saleAmt(s.getSaleAmt())
                .build());
    }

    public Page<SelOrderSaleGroupDto> findBySaleMonth(String store, String frDt, String toDt, int page) {
        Pageable pageable = PageRequest.of(page, 30, Sort.by(Sort.Direction.DESC, "saleDt"));

        return selOrderSaleRepository.findBySaleMonth(store, frDt, toDt, pageable).map(s-> SelOrderSaleGroupDto.builder()
                .saleDt(s.getSaleDt())
                .saleCnt(s.getSaleCnt())
                .saleAmt(s.getSaleAmt())
                .build());
    }

}
