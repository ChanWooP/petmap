package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.config.redis.MyRedis;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserPointHis;
import com.cwpark.petmap.petmap.data.domain.UserQna;
import com.cwpark.petmap.petmap.data.dto.UserDto;
import com.cwpark.petmap.petmap.data.dto.UserPointHisDto;
import com.cwpark.petmap.petmap.data.dto.UserQnaDto;
import com.cwpark.petmap.petmap.data.persistence.UserPointHisRepository;
import com.cwpark.petmap.petmap.data.persistence.UserQnaRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;

import java.util.concurrent.TimeUnit;

@Log
@Repository
public class UserQnaDao {
  private final UserQnaRepository userQnaRepository;

  private final MyRedis myRedis;
  @Autowired
  public UserQnaDao(UserQnaRepository userQnaRepository, MyRedis myRedis) {
    this.userQnaRepository = userQnaRepository;
    this.myRedis = myRedis;
  }

  public void postUserQna(UserQna userQna) {
    TransactionStatus transactionStatus = myRedis.startDBTransacton();

    try {
      if(myRedis.tryLock("userQnaLock", TimeUnit.SECONDS, 60, 10)) {
        int qnaId = userQnaRepository.getQnaId(userQna.getUser().getUserId());

        userQnaRepository.save(UserQna.builder()
                        .user(userQna.getUser())
                        .qnaId(qnaId)
                        .qnaQuestion(userQna.getQnaQuestion())
                        .qnaAnswer(userQna.getQnaAnswer())
                        .qnaAnswerYn(userQna.getQnaAnswerYn())
                .build());

        myRedis.commitDB(transactionStatus);
      }
    } catch (Exception e) {
      e.printStackTrace();
      myRedis.rollbackDB(transactionStatus);
    } finally {
      if(myRedis.canUnlock("userQnaLock")) {
        myRedis.unlock("userQnaLock");
      }
    }
  }

  public void putUserQna(UserQna userQna) {
    userQnaRepository.save(UserQna.builder()
            .user(userQna.getUser())
            .qnaId(userQna.getQnaId())
            .qnaQuestion(userQna.getQnaQuestion())
            .qnaAnswer(userQna.getQnaAnswer())
            .qnaAnswerYn(userQna.getQnaAnswerYn())
            .build());
  }

  public Page<UserQnaDto> findByUser(User user, int page) {
    Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "qnaId"));
    Page<UserQna> pages = userQnaRepository.findByUser(user, pageable);
    Page<UserQnaDto> rtnPages = null;

    if(pages.getContent().isEmpty()) {
      throw new EmptyResultDataAccessException(1);
    }

    rtnPages = pages.map(u -> UserQnaDto.builder()
            .userDto(UserDto.builder()
                    .userId(u.getUser().getUserId())
                    .userPassWord(u.getUser().getUserPassWord())
                    .userName(u.getUser().getUserName())
                    .userEmail(u.getUser().getUserEmail())
                    .userPhone(u.getUser().getUserPhone())
                    .userZipcode(u.getUser().getUserZipcode())
                    .userAddress1(u.getUser().getUserAddress1())
                    .userAddress2(u.getUser().getUserAddress2())
                    .userRole(u.getUser().getUserRole())
                    .userLoginFailCnt(u.getUser().getUserLoginFailCnt())
                    .userBizNo(u.getUser().getUserBizNo())
                    .userBirth(u.getUser().getUserBirth())
                    .userSex(u.getUser().getUserSex())
                    .oAuthType(u.getUser().getOAuthType())
                    .userBizNo(u.getUser().getUserBizNo())
                    .userBizName(u.getUser().getUserBizName())
                    .build())
            .qnaId(u.getQnaId())
            .qnaQuestion(u.getQnaQuestion())
            .qnaAnswer(u.getQnaAnswer())
            .qnaAnswerYn(u.getQnaAnswerYn())
            .build());

    return rtnPages;
  }

  public Page<UserQnaDto> findByAll(int page) {
    Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "qnaId"));
    Page<UserQna> pages = userQnaRepository.findAll(pageable);
    Page<UserQnaDto> rtnPages = null;

    if(pages.getContent().isEmpty()) {
      throw new EmptyResultDataAccessException(1);
    }

    rtnPages = pages.map(u -> UserQnaDto.builder()
            .userDto(UserDto.builder()
                    .userId(u.getUser().getUserId())
                    .userPassWord(u.getUser().getUserPassWord())
                    .userName(u.getUser().getUserName())
                    .userEmail(u.getUser().getUserEmail())
                    .userPhone(u.getUser().getUserPhone())
                    .userZipcode(u.getUser().getUserZipcode())
                    .userAddress1(u.getUser().getUserAddress1())
                    .userAddress2(u.getUser().getUserAddress2())
                    .userRole(u.getUser().getUserRole())
                    .userLoginFailCnt(u.getUser().getUserLoginFailCnt())
                    .userBizNo(u.getUser().getUserBizNo())
                    .userBirth(u.getUser().getUserBirth())
                    .userSex(u.getUser().getUserSex())
                    .oAuthType(u.getUser().getOAuthType())
                    .userBizNo(u.getUser().getUserBizNo())
                    .userBizName(u.getUser().getUserBizName())
                    .build())
            .qnaId(u.getQnaId())
            .qnaQuestion(u.getQnaQuestion())
            .qnaAnswer(u.getQnaAnswer())
            .qnaAnswerYn(u.getQnaAnswerYn())
            .build());

    return rtnPages;
  }
}
