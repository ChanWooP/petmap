package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.ItemDao;
import com.cwpark.petmap.petmap.data.dao.StockDao;
import com.cwpark.petmap.petmap.data.domain.Category;
import com.cwpark.petmap.petmap.data.domain.Item;
import com.cwpark.petmap.petmap.data.domain.Stock;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.dto.StockDto;
import com.cwpark.petmap.petmap.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


@Service
public class StockService {
    private final StockDao stockDao;

    @Autowired
    public StockService(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    public void postStock(StockDto stockDto) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String strToday = sdf.format(c1.getTime());

        Stock stock = Stock.builder()
                .item(Item.builder()
                        .user(User.builder().userId(stockDto.getItemDto().getUser()).build())
                        .selItemId(stockDto.getItemDto().getSelItemId())
                        .build())
                .stockDt(strToday)
                .stockQty(stockDto.getStockQty())
                .stockExpln(stockDto.getStockExpln())
                .build();

        stockDao.postStock(stock);
    }

    public Page<StockDto> getStock(String user, String item, String frDt, String toDt, int page) {
        return stockDao.getStock(user, item, frDt, toDt, page);
    }

}
