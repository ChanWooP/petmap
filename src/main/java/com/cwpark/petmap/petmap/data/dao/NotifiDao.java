package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.config.redis.MyRedis;
import com.cwpark.petmap.petmap.data.classid.ItemClassId;
import com.cwpark.petmap.petmap.data.domain.*;
import com.cwpark.petmap.petmap.data.dto.ItemDto;
import com.cwpark.petmap.petmap.data.dto.NotifiDto;
import com.cwpark.petmap.petmap.data.persistence.ItemRepository;
import com.cwpark.petmap.petmap.data.persistence.NotifiRepository;
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
public class NotifiDao {
    private final NotifiRepository notifiRepository;

    @Autowired
    public NotifiDao(NotifiRepository notifiRepository) {
        this.notifiRepository = notifiRepository;
    }

    public void postNotifi(Notifi notifi) {
        notifiRepository.save(notifi);
    }

    public void deleteNotifi(Notifi notifi) {
        notifiRepository.delete(notifi);
    }

    public Page<NotifiDto> getNotifi(int page) {
        Pageable pageable = PageRequest.of(page,10, Sort.by(Sort.Direction.DESC, "regDate"));

        Page<Notifi> list = notifiRepository.findAll(pageable);
        Page<NotifiDto> result = null;

        if(list.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        } else {
            result = list.map(n -> NotifiDto.builder()
                    .notifiId(n.getNotifiId())
                    .notifiType(n.getNotifiType())
                    .notifiTitle(n.getNotifiTitle())
                    .notifiText(n.getNotifiText())
                    .notifiBannerImg(n.getNotifiBannerImg())
                    .notifiMainImg(n.getNotifiMainImg())
                    .notifiStartDt(n.getNotifiStartDt())
                    .notifiEndDt(n.getNotifiEndDt())
                    .build());
        }

        return result;
    }

    public List<NotifiDto> getNotifiEvent(String type, String now) {
        List<Notifi> list = notifiRepository.getNotifiEvent(type, now);
        List<NotifiDto> result = null;

        if(list.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        } else {
            result = list.stream().map(n -> NotifiDto.builder()
                    .notifiId(n.getNotifiId())
                    .notifiType(n.getNotifiType())
                    .notifiTitle(n.getNotifiTitle())
                    .notifiText(n.getNotifiText())
                    .notifiBannerImg(n.getNotifiBannerImg())
                    .notifiMainImg(n.getNotifiMainImg())
                    .notifiStartDt(n.getNotifiStartDt())
                    .notifiEndDt(n.getNotifiEndDt())
                    .build()).collect(Collectors.toList());
        }

        return result;
    }
}
