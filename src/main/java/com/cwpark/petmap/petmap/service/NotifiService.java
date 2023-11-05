package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.ItemDao;
import com.cwpark.petmap.petmap.data.dao.NotifiDao;
import com.cwpark.petmap.petmap.data.domain.Category;
import com.cwpark.petmap.petmap.data.domain.Item;
import com.cwpark.petmap.petmap.data.domain.Notifi;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.dto.NotifiDto;
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
public class NotifiService {
    private final NotifiDao notifiDao;

    @Autowired
    public NotifiService(NotifiDao notifiDao) {
        this.notifiDao = notifiDao;
    }

    public void postNotifi(NotifiDto notifiDto, List<MultipartFile> files) throws IOException {
        String saveFileNameMini = notifiDto.getNotifiBannerImg();
        String saveFileNameMain = notifiDto.getNotifiMainImg();

        if(files != null) {
            for (MultipartFile m : files) {
                if (m.getOriginalFilename().equals("mini")) {
                    saveFileNameMini = FileUtils.singleFileUpload(m, "notifi");
                } else {
                    saveFileNameMain = FileUtils.singleFileUpload(m, "notifi");
                }
            }
        }

        notifiDao.postNotifi(Notifi.builder()
                        .notifiId(notifiDto.getNotifiId())
                        .notifiType(notifiDto.getNotifiType())
                        .notifiTitle(notifiDto.getNotifiTitle())
                        .notifiText(notifiDto.getNotifiText())
                        .notifiBannerImg(saveFileNameMini)
                        .notifiMainImg(saveFileNameMain)
                        .notifiStartDt(notifiDto.getNotifiStartDt())
                        .notifiEndDt(notifiDto.getNotifiEndDt())
                .build());
    }

    public void deleteNotifi(NotifiDto notifiDto) {
        notifiDao.deleteNotifi(Notifi.builder().notifiId(notifiDto.getNotifiId()).build());
    }

    public Page<NotifiDto> getNotifi(int page) {
        return notifiDao.getNotifi(page);
    }

    public List<NotifiDto> getNotifiEvent() {
        String type = "event";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String now = sdf.format(c1.getTime());

        return notifiDao.getNotifiEvent(type, now);
    }

}
