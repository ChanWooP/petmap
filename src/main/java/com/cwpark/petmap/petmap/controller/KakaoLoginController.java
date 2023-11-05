package com.cwpark.petmap.petmap.controller;

import com.cwpark.petmap.petmap.service.KakaoLoginService;
import com.cwpark.petmap.petmap.service.UserService;
import com.cwpark.petmap.petmap.data.dto.UserDto;
import com.cwpark.petmap.petmap.data.enums.OAuthType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KakaoLoginController {

  private final KakaoLoginService kakaoLoginService;
  private final UserService userService;
  private final AuthenticationManager authenticationManager;

  @Value("${kakao.default.password}")
  private String kakaoPassword;

  @Autowired
  public KakaoLoginController(KakaoLoginService kakaoLoginService, UserService userService, AuthenticationManager authenticationManager) {
    this.kakaoLoginService = kakaoLoginService;
    this.userService = userService;
    this.authenticationManager = authenticationManager;
  }

  @GetMapping("/oauth/kakao")
  public String kakaoCallback(String code, Model model) {
    String accessToken = kakaoLoginService.getAccessToken(code);
    UserDto kakaoUser = kakaoLoginService.getUserInfo(accessToken);

    if(!userService.existsUser(kakaoUser.getUserEmail(), "Email")) {
      userService.saveUser(kakaoUser);
    } else {
      kakaoUser = userService.getUser(kakaoUser.getUserEmail(), "email");

      if(!kakaoUser.getOAuthType().equals(OAuthType.KAKAO)) {
        model.addAttribute("error", "OAuth");
        return "error/error";
      }
    }

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        kakaoUser.getUserEmail(), kakaoPassword
    );

    Authentication authentication= authenticationManager.authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return "redirect:/";
  }
}
