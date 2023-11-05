package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.UserPointHisDao;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserPointHis;
import com.cwpark.petmap.petmap.data.dto.UserDto;
import com.cwpark.petmap.petmap.data.dto.UserPointHisDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class UserPointHisService {
    private final UserPointHisDao userPointHisDao;

    @Autowired
    public UserPointHisService(UserPointHisDao userPointHisDao) {
        this.userPointHisDao = userPointHisDao;
    }

    public void postUserPointHis(UserPointHisDto userPointHisDto) {
        User user = User.builder()
                .userId(userPointHisDto.getUserDto().getUserId())
                .userPassWord("0")
                .userEmail("0")
                .userRole("0")
                .userLoginFailCnt(0)
                .userBizNo("0")
                .userBizName("0")
                .build();

        UserPointHis userPointHis = UserPointHis.builder()
                .user(user)
                .pointId(userPointHisDto.getPointId())
                .pointExpln(userPointHisDto.getPointExpln())
                .pointNum(userPointHisDto.getPointNum())
                .pointReason(userPointHisDto.getPointReason())
                .build();

        userPointHisDao.postUserPointHis(userPointHis);
    }

    public Page<UserPointHisDto> findByUser(UserDto userDto, int page) {
        User user = User.builder()
                .userId(userDto.getUserId())
                .userPassWord(userDto.getUserPassWord())
                .userName(userDto.getUserName())
                .userEmail(userDto.getUserEmail())
                .userPhone(userDto.getUserPhone())
                .userZipcode(userDto.getUserZipcode())
                .userAddress1(userDto.getUserAddress1())
                .userAddress2(userDto.getUserAddress2())
                .userRole(userDto.getUserRole())
                .userLoginFailCnt(userDto.getUserLoginFailCnt())
                .userBizNo(userDto.getUserBizNo())
                .userBirth(userDto.getUserBirth())
                .userSex(userDto.getUserSex())
                .oAuthType(userDto.getOAuthType())
                .userBizNo(userDto.getUserBizNo())
                .userBizName(userDto.getUserBizName())
                .build();

        return userPointHisDao.findByUser(user, page);
    }

}
