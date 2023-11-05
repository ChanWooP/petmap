package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.config.redis.MyRedis;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserPointHis;
import com.cwpark.petmap.petmap.data.dto.UserCouponDto;
import com.cwpark.petmap.petmap.data.dto.UserDto;
import com.cwpark.petmap.petmap.data.dto.UserPointHisDto;
import com.cwpark.petmap.petmap.data.persistence.UserPointHisRepository;
import com.cwpark.petmap.petmap.data.persistence.UserRepository;
import lombok.extern.java.Log;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Log
@Repository
public class UserPointHisDao {
  private final UserPointHisRepository userPointHisRepository;
  private final MyRedis myRedis;
  @Autowired
  public UserPointHisDao(UserPointHisRepository userPointHisRepository, MyRedis myRedis) {
    this.userPointHisRepository = userPointHisRepository;
    this.myRedis = myRedis;
  }

  public void postUserPointHis(UserPointHis userPointHis) {
    TransactionStatus transactionStatus = myRedis.startDBTransacton();

    try {
      if(myRedis.tryLock("userPointHisLock", TimeUnit.SECONDS, 60, 10)) {
        int pointId = userPointHisRepository.getPointId(userPointHis.getUser().getUserId());

        userPointHisRepository.save(UserPointHis.builder()
                        .user(userPointHis.getUser())
                        .pointId(pointId)
                        .pointNum(userPointHis.getPointNum())
                        .pointReason(userPointHis.getPointReason())
                        .pointExpln(userPointHis.getPointExpln())
                .build());

        myRedis.commitDB(transactionStatus);
      }
    } catch (Exception e) {
      e.printStackTrace();
      myRedis.rollbackDB(transactionStatus);
    } finally {
      if(myRedis.canUnlock("userPointHisLock")) {
        myRedis.unlock("userPointHisLock");
      }
    }
  }

  public Page<UserPointHisDto> findByUser(User user, int page) {
    Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "pointId"));
    Page<UserPointHis> pages = userPointHisRepository.findByUser(user, pageable);
    Page<UserPointHisDto> rtnPages = null;

    if(pages.getContent().isEmpty()) {
      throw new EmptyResultDataAccessException(1);
    }

    rtnPages = pages.map(u -> UserPointHisDto.builder()
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
            .pointNum(u.getPointNum())
            .pointReason(u.getPointReason())
            .pointExpln(u.getPointExpln())
            .pointId(u.getPointId())
            .build());

    return rtnPages;
  }
}
