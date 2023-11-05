package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.config.security.UserDetailsImpl;
import com.cwpark.petmap.petmap.data.dao.UserDao;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.dto.UserDto;
import com.cwpark.petmap.petmap.data.enums.OAuthType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserDao userDao;

  @Autowired
  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public Boolean existsUser(String userId, String type) {
    return userDao.existsUser(userId, type);
  }

  public UserDto getUser(String user, String type) {
    return userDao.getUser(user, type);
  }

  public Page<UserDto> getUser(String userId, int page) {
    return userDao.getUser(userId, page);
  }

  public UserDto saveUser(UserDto userDto) {
    UserDto rtnUserDto = null;
    User user = new User();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    OAuthType oAuthType = userDto.getOAuthType() == null ? OAuthType.PETMAP : userDto.getOAuthType();
    Authentication auths = SecurityContextHolder.getContext().getAuthentication();

    user = User.builder()
            .userId(userDto.getUserId())
            .userPassWord(passwordEncoder.encode(userDto.getUserPassWord()))
            .userName(userDto.getUserName())
            .userBirth(userDto.getUserBirth())
            .userSex(userDto.getUserSex())
            .userEmail(userDto.getUserEmail())
            .userPhone(userDto.getUserPhone())
            .userZipcode(userDto.getUserZipcode())
            .userAddress1(userDto.getUserAddress1())
            .userAddress2(userDto.getUserAddress2())
            .userRole(userDto.getUserRole())
            .userLoginFailCnt(userDto.getUserLoginFailCnt())
            .oAuthType(oAuthType)
            .userBizNo(userDto.getUserBizNo())
            .userBizName(userDto.getUserBizName())
            .userPoint(userDto.getUserPoint())
            .build();

    if(!(auths.getPrincipal() instanceof String)) {
      if(auths.getName().equals(user.getUserId())) {
        UserDetailsImpl users = (UserDetailsImpl) auths.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(auths, user));

        if(users.getPassword().equals(userDto.getUserPassWord())) {
          user.setUserPassWord(userDto.getUserPassWord());
        } else {
          user.setUserPassWord(passwordEncoder.encode(userDto.getUserPassWord()));
        }
      } else {
        String password = userDao.getUser(user.getUserId(), "id").getUserPassWord();

        if(password.equals(userDto.getUserPassWord())) {
          user.setUserPassWord(userDto.getUserPassWord());
        } else {
          user.setUserPassWord(passwordEncoder.encode(userDto.getUserPassWord()));
        }
      }
    }

    return userDao.saveUser(user);
  }

  public boolean useUserPoint(String userId, int point, String type) {
    return userDao.useUserPoint(userId, point, type);
  }

  protected Authentication createNewAuthentication(Authentication currentAuth, User user) {
    UserDetails newPrincipal = new UserDetailsImpl(user);
    UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(newPrincipal, currentAuth.getCredentials(), newPrincipal.getAuthorities());
    newAuth.setDetails(currentAuth.getDetails());
    return newAuth;
  }
}
