package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.data.classid.SelOrderClassId;
import com.cwpark.petmap.petmap.data.classid.UserOrderClassId;
import com.cwpark.petmap.petmap.data.domain.SelOrder;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserOrder;
import com.cwpark.petmap.petmap.data.dto.SelOrderDto;
import com.cwpark.petmap.petmap.data.dto.UserOrderDto;
import com.cwpark.petmap.petmap.data.persistence.SelOrderRepository;
import com.cwpark.petmap.petmap.data.persistence.UserOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SelOrderDao {
    private final SelOrderRepository selOrderRepository;

    @Autowired
    public SelOrderDao(SelOrderRepository selOrderRepository) {
        this.selOrderRepository = selOrderRepository;
    }

    public Long getSelOrderId(String user) {
        return selOrderRepository.getUserOrderId(user);
    }

    public SelOrderDto saveSelOrder(SelOrder selOrder) {
        SelOrder result = selOrderRepository.save(selOrder);

        return SelOrderDto.builder()
                .user(result.getUser().getUserId())
                .ordDt(result.getOrdDt())
                .ordId(result.getOrdId())
                .userOrderDto(UserOrderDto.builder()
                        .user(result.getUserOrder().getUser().getUserId())
                        .ordDt(result.getUserOrder().getOrdDt())
                        .ordId(result.getUserOrder().getOrdId())
                        .ordAddress(result.getUserOrder().getOrdAddress())
                        .ordName(result.getUserOrder().getOrdName())
                        .ordPhone(result.getUserOrder().getOrdPhone())
                        .ordTotAmt(result.getUserOrder().getOrdTotAmt())
                        .ordCouDc(result.getUserOrder().getOrdCouDc())
                        .ordPoiDc(result.getUserOrder().getOrdPoiDc())
                        .ordDelAmt(result.getUserOrder().getOrdDelAmt())
                        .ordNetAmt(result.getUserOrder().getOrdNetAmt())
                        .build())
                .ordStatus(result.getOrdStatus())
                .ordInvoice(result.getOrdInvoice())
                .ordTotAmt(result.getOrdTotAmt())
                .ordDelAmt(result.getOrdDelAmt())
                .build();
    }

    public void deleteSelOrder(SelOrder selOrder) {
        selOrderRepository.delete(selOrder);
    }

    public SelOrderDto getOneSelOrder(String user, String ordDt, Long ordId) {
        SelOrderClassId selOrderClassId = SelOrderClassId.builder()
                .user(user)
                .ordDt(ordDt)
                .ordId(ordId)
                .build();

        Optional<SelOrder> optional = selOrderRepository.findById(selOrderClassId);
        SelOrder selOrder = optional.orElse(null);

        if(selOrder != null) {
            return SelOrderDto.builder()
                    .user(selOrder.getUser().getUserId())
                    .ordDt(selOrder.getOrdDt())
                    .ordId(selOrder.getOrdId())
                    .userOrderDto(UserOrderDto.builder()
                            .user(selOrder.getUserOrder().getUser().getUserId())
                            .ordDt(selOrder.getUserOrder().getOrdDt())
                            .ordId(selOrder.getUserOrder().getOrdId())
                            .ordAddress(selOrder.getUserOrder().getOrdAddress())
                            .ordName(selOrder.getUserOrder().getOrdName())
                            .ordPhone(selOrder.getUserOrder().getOrdPhone())
                            .ordTotAmt(selOrder.getUserOrder().getOrdTotAmt())
                            .ordCouDc(selOrder.getUserOrder().getOrdCouDc())
                            .ordPoiDc(selOrder.getUserOrder().getOrdPoiDc())
                            .ordDelAmt(selOrder.getUserOrder().getOrdDelAmt())
                            .ordNetAmt(selOrder.getUserOrder().getOrdNetAmt())
                            .build())
                    .ordStatus(selOrder.getOrdStatus())
                    .ordInvoice(selOrder.getOrdInvoice())
                    .ordTotAmt(selOrder.getOrdTotAmt())
                    .ordDelAmt(selOrder.getOrdDelAmt())
                    .build();
        } else {
            return null;
        }
    }

    public Page<SelOrderDto> getListSelOrder(String user, String frDt, String toDt, int page) throws Exception {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "regDate"));
        Page<SelOrder> result = selOrderRepository.findByUserAndOrdDtBetween(User.builder().userId(user).build(), frDt, toDt, pageable);

        if(result.getContent().isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return result.map(s -> SelOrderDto.builder()
                .user(s.getUser().getUserId())
                .ordDt(s.getOrdDt())
                .ordId(s.getOrdId())
                .userOrderDto(UserOrderDto.builder()
                        .user(s.getUserOrder().getUser().getUserId())
                        .ordDt(s.getUserOrder().getOrdDt())
                        .ordId(s.getUserOrder().getOrdId())
                        .ordAddress(s.getUserOrder().getOrdAddress())
                        .ordName(s.getUserOrder().getOrdName())
                        .ordPhone(s.getUserOrder().getOrdPhone())
                        .ordTotAmt(s.getUserOrder().getOrdTotAmt())
                        .ordCouDc(s.getUserOrder().getOrdCouDc())
                        .ordPoiDc(s.getUserOrder().getOrdPoiDc())
                        .ordDelAmt(s.getUserOrder().getOrdDelAmt())
                        .ordNetAmt(s.getUserOrder().getOrdNetAmt())
                        .build())
                .ordStatus(s.getOrdStatus())
                .ordInvoice(s.getOrdInvoice())
                .ordTotAmt(s.getOrdTotAmt())
                .ordDelAmt(s.getOrdDelAmt())
                .build());
    }

    public List<SelOrderDto> findByOrdDtAndUserOrder(String ordDt, UserOrder userOrder) {
        List<SelOrder> list = selOrderRepository.findByOrdDtAndUserOrder(ordDt, userOrder);

        return list.stream().map(s -> SelOrderDto.builder()
                .user(s.getUser().getUserId()+"/"+s.getUser().getUserBizName())
                .ordDt(s.getOrdDt())
                .ordId(s.getOrdId())
                .userOrderDto(UserOrderDto.builder()
                        .user(s.getUserOrder().getUser().getUserId())
                        .ordDt(s.getUserOrder().getOrdDt())
                        .ordId(s.getUserOrder().getOrdId())
                        .ordAddress(s.getUserOrder().getOrdAddress())
                        .ordName(s.getUserOrder().getOrdName())
                        .ordPhone(s.getUserOrder().getOrdPhone())
                        .ordTotAmt(s.getUserOrder().getOrdTotAmt())
                        .ordCouDc(s.getUserOrder().getOrdCouDc())
                        .ordPoiDc(s.getUserOrder().getOrdPoiDc())
                        .ordDelAmt(s.getUserOrder().getOrdDelAmt())
                        .ordNetAmt(s.getUserOrder().getOrdNetAmt())
                        .build())
                .ordStatus(s.getOrdStatus())
                .ordInvoice(s.getOrdInvoice())
                .ordTotAmt(s.getOrdTotAmt())
                .ordDelAmt(s.getOrdDelAmt())
                .build()).collect(Collectors.toList());
    }

}
