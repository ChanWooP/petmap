package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dao.UserQnaDao;
import com.cwpark.petmap.petmap.data.domain.User;
import com.cwpark.petmap.petmap.data.domain.UserQna;
import com.cwpark.petmap.petmap.data.dto.UserQnaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class UserQnaService {
    private final UserQnaDao userQnaDao;

    @Autowired
    public UserQnaService(UserQnaDao userQnaDao) {
        this.userQnaDao = userQnaDao;
    }

    public void postUserQna(UserQnaDto userQnaDto) {
        User user = User.builder()
                .userId(userQnaDto.getUserDto().getUserId())
                .userPassWord("0")
                .userEmail("0")
                .userRole("0")
                .userLoginFailCnt(0)
                .userBizNo("0")
                .userBizName("0")
                .build();

        UserQna userQna = UserQna.builder()
                .user(user)
                .qnaId(userQnaDto.getQnaId())
                .qnaQuestion(userQnaDto.getQnaQuestion())
                .qnaAnswer(userQnaDto.getQnaAnswer())
                .qnaAnswerYn(userQnaDto.getQnaAnswerYn())
                .build();

        userQnaDao.postUserQna(userQna);
    }

    public void putUserQna(UserQnaDto userQnaDto) {
        User user = User.builder()
                .userId(userQnaDto.getUserDto().getUserId())
                .userPassWord("0")
                .userEmail("0")
                .userRole("0")
                .userLoginFailCnt(0)
                .userBizNo("0")
                .userBizName("0")
                .build();

        UserQna userQna = UserQna.builder()
                .user(user)
                .qnaId(userQnaDto.getQnaId())
                .qnaQuestion(userQnaDto.getQnaQuestion())
                .qnaAnswerYn(userQnaDto.getQnaAnswerYn())
                .build();

        userQnaDao.putUserQna(userQna);
    }

    public void putAdminUserQna(UserQnaDto userQnaDto) {
        User user = User.builder()
                .userId(userQnaDto.getUserDto().getUserId())
                .userPassWord("0")
                .userEmail("0")
                .userRole("0")
                .userLoginFailCnt(0)
                .userBizNo("0")
                .userBizName("0")
                .build();

        UserQna userQna = UserQna.builder()
                .user(user)
                .qnaId(userQnaDto.getQnaId())
                .qnaAnswer(userQnaDto.getQnaAnswer())
                .qnaAnswerYn(userQnaDto.getQnaAnswerYn())
                .build();

        userQnaDao.putUserQna(userQna);
    }

    public Page<UserQnaDto> findByUser(String userId, int page) {
        User user = user = User.builder()
                .userId(userId)
                .userPassWord("0")
                .userEmail("0")
                .userRole("0")
                .userLoginFailCnt(0)
                .userBizNo("0")
                .userBizName("0")
                .build();

        return userQnaDao.findByUser(user, page);
    }

    public Page<UserQnaDto> findByAll(int page) {
        return userQnaDao.findByAll(page);
    }

}
