package com.cwpark.petmap.petmap.data.dao;

import com.cwpark.petmap.petmap.config.redis.MyRedis;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.dto.CouponDto;
import com.cwpark.petmap.petmap.data.dto.UserDto;
import com.cwpark.petmap.petmap.data.persistence.UserRepository;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;

@Repository
public class UserDao {
  private final UserRepository userRepository;
  private final MyRedis myRedis;

  @Autowired
  public UserDao(UserRepository userRepository, MyRedis myRedis) {
    this.userRepository = userRepository;
    this.myRedis = myRedis;
  }

  public Boolean existsUser(String userId, String type) {
    if(type.equals("Id")) {
      return userRepository.existsById(userId);
    } else {
      return userRepository.existsByUserEmail(userId);
    }
  }

  public UserDto getUser(String user, String type) {
    Optional<User> optional =  null;
    User rtnUser = null;

    if(type.equals("id")) {
      optional = userRepository.findById(user);
    } else {
      optional = userRepository.findByUserEmail(user);
    }

    if(optional.isPresent()) {
      rtnUser = optional.get();
    } else {
      throw new NullPointerException("사용자가 존재하지 않습니다");
    }

    return UserDto.builder()
            .userId(rtnUser.getUserId())
            .userPassWord(rtnUser.getUserPassWord())
            .userName(rtnUser.getUserName())
            .userEmail(rtnUser.getUserEmail())
            .userPhone(rtnUser.getUserPhone())
            .userZipcode(rtnUser.getUserZipcode())
            .userAddress1(rtnUser.getUserAddress1())
            .userAddress2(rtnUser.getUserAddress2())
            .userRole(rtnUser.getUserRole())
            .userLoginFailCnt(rtnUser.getUserLoginFailCnt())
            .userBizNo(rtnUser.getUserBizNo())
            .userBirth(rtnUser.getUserBirth())
            .userSex(rtnUser.getUserSex())
            .oAuthType(rtnUser.getOAuthType())
            .userBizNo(rtnUser.getUserBizNo())
            .userBizName(rtnUser.getUserBizName())
            .userPoint(rtnUser.getUserPoint())
            .build();
  }

  public Page<UserDto> getUser(String userId, int page) {
    Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "userId"));
    String user = userId.equals("all") ? "" : userId;
    Page<User> userPage = userRepository.findByUserIdContaining(user, pageable);
    Page<UserDto> userDtoPage = null;

    if(userPage.getContent().isEmpty()) {
      throw new EmptyResultDataAccessException(1);
    } else {
      userDtoPage = userPage.map(u -> UserDto.builder()
              .userId(u.getUserId())
              .userPassWord(u.getUserPassWord())
              .userName(u.getUserName())
              .userEmail(u.getUserEmail())
              .userPhone(u.getUserPhone())
              .userZipcode(u.getUserZipcode())
              .userAddress1(u.getUserAddress1())
              .userAddress2(u.getUserAddress2())
              .userRole(u.getUserRole())
              .userLoginFailCnt(u.getUserLoginFailCnt())
              .userBizNo(u.getUserBizNo())
              .userBirth(u.getUserBirth())
              .userSex(u.getUserSex())
              .oAuthType(u.getOAuthType())
              .userBizNo(u.getUserBizNo())
              .userBizName(u.getUserBizName())
              .userPoint(u.getUserPoint())
              .build());
    }

    return userDtoPage;
  }

  public UserDto saveUser(User user) {
    User rtnUser = null;

    rtnUser = userRepository.save(user);

    return UserDto.builder()
            .userId(rtnUser.getUserId())
            .userPassWord(rtnUser.getUserPassWord())
            .userName(rtnUser.getUserName())
            .userBirth(rtnUser.getUserBirth())
            .userSex(rtnUser.getUserSex())
            .userEmail(rtnUser.getUserEmail())
            .userPhone(rtnUser.getUserPhone())
            .userZipcode(rtnUser.getUserZipcode())
            .userAddress1(rtnUser.getUserAddress1())
            .userAddress2(rtnUser.getUserAddress2())
            .userRole(rtnUser.getUserRole())
            .userLoginFailCnt(rtnUser.getUserLoginFailCnt())
            .userBizNo(rtnUser.getUserBizNo())
            .userBizName(rtnUser.getUserBizName())
            .userPoint(rtnUser.getUserPoint())
            .build();
  }

  public boolean useUserPoint(String userId, int point, String type) {
    TransactionStatus transactionStatus = myRedis.startDBTransacton();
    Boolean bool = true;

    try {
      if(myRedis.tryLock("useUserPoint", TimeUnit.SECONDS, 60, 10)) {

        Optional<User> optional = userRepository.findById(userId);
        User user = null;
        int userPoint = 0;

        if(optional.isPresent()) {
          user = optional.get();
          userPoint = user.getUserPoint();
        } else {
          bool = false;
        }

        if(type.equals("-")) {
          if(userPoint < point) {
            bool = false;
          } else {
            user.setUserPoint(userPoint - point);
          }
        } else {
          user.setUserPoint(userPoint + point);
        }

        myRedis.commitDB(transactionStatus);
      }
    } catch (Exception e) {
      e.printStackTrace();
      myRedis.rollbackDB(transactionStatus);
    } finally {
      if(myRedis.canUnlock("useUserPoint")) {
        myRedis.unlock("useUserPoint");
      }
    }

    return bool;
  }


}
