package com.cwpark.petmap.petmap.service;

import com.cwpark.petmap.petmap.data.dto.UserDto;
import com.cwpark.petmap.petmap.data.enums.OAuthType;
import com.cwpark.petmap.petmap.data.enums.RoleType;
import com.google.gson.Gson;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoLoginService {

  @Value("${kakao.default.password}")
  private String kakaoPassword;

  public String getAccessToken(String code) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", "3698044e9a8c3cc9174ea3f803373481");
    body.add("redirect_uri", "http://localhost:8080/oauth/kakao");
    body.add("code", code);

    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity responseEntity = restTemplate.exchange("https://kauth.kakao.com/oauth/token",
        HttpMethod.POST, requestEntity, String.class);

    String jsonData = (String) responseEntity.getBody();

    Gson gson = new Gson();
    Map<?, ?> data = gson.fromJson(jsonData, Map.class);

    return (String) data.get("access_token");
  }

  public UserDto getUserInfo(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> responseEntity = restTemplate.exchange(
        "https://kapi.kakao.com/v2/user/me"
        ,HttpMethod.POST
        ,requestEntity
        ,String.class
    );

    String userInfo = responseEntity.getBody();

    Gson gson = new Gson();
    Map<?, ?> data = gson.fromJson(userInfo, Map.class);

    Double id = (Double) (data.get("id"));
    String nickName = (String) ((Map<?, ?>) (data.get("properties"))).get("nickname");
    String email = (String) ((Map<?, ?>) (data.get("kakao_account"))).get("email");

    UserDto user = new UserDto();
    user.setUserId(email);
    user.setUserPassWord(kakaoPassword);
    user.setUserName(nickName);
    user.setUserEmail(email);
    user.setUserRole(RoleType.BUYER.name());
    user.setUserLoginFailCnt(0);
    user.setOAuthType(OAuthType.KAKAO);
    user.setUserBizNo("0");

    return user;
  }
}
