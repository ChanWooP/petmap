package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.config.exception.CountException;
import com.cwpark.petmap.petmap.data.dao.UserAddressDao;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserAddress;
import com.cwpark.petmap.petmap.data.dto.UserAddressDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class UserAddressService {

  private final UserAddressDao userAddressDao;

  @Autowired
  public UserAddressService(UserAddressDao userAddressDao) {
    this.userAddressDao = userAddressDao;
  }

  public void postAddressUser(UserAddressDto userAddressDto) throws Exception {
    if(userAddressDao.getUserAddressCnt(User.builder().userId(userAddressDto.getUser()).build()) >= 5) {
      throw new CountException("주소는 5개까지만 등록이 가능합니다");
    }

    userAddressDao.postUserAddress(UserAddress.builder()
                    .user(User.builder().userId(userAddressDto.getUser()).build())
                    .addressUserPhone(userAddressDto.getAddressUserPhone())
                    .addressUserZipcode(userAddressDto.getAddressUserZipcode())
                    .addressUserAddress1(userAddressDto.getAddressUserAddress1())
                    .addressUserAddress2(userAddressDto.getAddressUserAddress2())
                    .addressUserName(userAddressDto.getAddressUserName())
            .build());
  }

  public void deleteAddressUser(UserAddressDto userAddressDto) {
    userAddressDao.deleteUserAddress(UserAddress.builder()
            .addressId(userAddressDto.getAddressId())
            .build());
  }

  public List<UserAddressDto> getAddressUser(String user) {
    return userAddressDao.getUserAddress(User.builder().userId(user).build());
  }
}
