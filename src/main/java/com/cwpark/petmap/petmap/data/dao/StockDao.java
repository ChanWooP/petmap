package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.config.redis.MyRedis;
import com.cwpark.petmap.petmap.data.classid.ItemClassId;
import com.cwpark.petmap.petmap.data.domain.*;
import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.dto.StockDto;
import com.cwpark.petmap.petmap.data.persistence.ItemRepository;
import com.cwpark.petmap.petmap.data.persistence.StockRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
public class StockDao {
    private final StockRepository stockRepository;

    private final MyRedis myRedis;

    @Autowired
    public StockDao(StockRepository stockRepository, MyRedis myRedis) {
        this.stockRepository = stockRepository;
        this.myRedis = myRedis;
    }

    public void postStock(Stock stock) {
        TransactionStatus transactionStatus = myRedis.startDBTransacton();

        try {
            if(myRedis.tryLock("StockLock", TimeUnit.SECONDS, 60, 10)) {
                Long stockId = stockRepository.getStockId(stock.getItem().getUser().getUserId(), stock.getItem().getSelItemId(), stock.getStockDt());

                stock.setStockId(stockId);

                stockRepository.save(stock);

                myRedis.commitDB(transactionStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            myRedis.rollbackDB(transactionStatus);
        } finally {
            if(myRedis.canUnlock("StockLock")) {
                myRedis.unlock("StockLock");
            }
        }
    }

    public Page<StockDto> getStock(String user, String item, String frDt, String toDt, int page) {
        BooleanBuilder builder = new BooleanBuilder();
        QStock qStock = QStock.stock;
        Pageable pageable = PageRequest.of(page,10, Sort.by(Sort.Direction.DESC, "regDate"));

        builder.and(qStock.item.eq(Item.builder().user(User.builder().userId(user).build()).selItemId(item).build()));
        builder.and(qStock.stockDt.between(frDt, toDt));

        Page<Stock> list = stockRepository.findAll(builder, pageable);
        Page<StockDto> result = null;

        if(list.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        } else {
            result = list.map(s -> StockDto.builder()
                    .itemDto(ItemDto.builder()
                            .user(s.getItem().getUser().getUserId())
                            .selItemId(s.getItem().getSelItemId())
                            .selItemName(s.getItem().getSelItemName())
                            .category(s.getItem().getCategory().getCategoryId())
                            .selMiniImg(s.getItem().getSelMiniImg())
                            .selMainImg(s.getItem().getSelMainImg())
                            .selExpln(s.getItem().getSelExpln())
                            .selItemPrice(s.getItem().getSelItemPrice())
                            .selDeilPrice(s.getItem().getSelDeilPrice())
                            .selStarPoint(s.getItem().getSelStarPoint())
                            .selSaleCount(s.getItem().getSelSaleCount())
                            .selHeartCount(s.getItem().getSelHeartCount())
                            .selStockCount(s.getItem().getSelStockCount())
                            .build())
                    .stockDt(s.getStockDt())
                    .stockId(s.getStockId())
                    .stockQty(s.getStockQty())
                    .stockExpln(s.getStockExpln())
                    .build());
        }

        return result;
    }

}
