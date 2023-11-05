package com.cwpark.petmap.petmap.controller;

import com.cwpark.petmap.petmap.service.UserService;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log
@RequestMapping(value="/auth")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  // 로그인 페이지
  @GetMapping("/login")
  public String login(@RequestParam(value = "error", required = false) String error
                     ,@RequestParam(value = "exception", required = false) String exception
                     , Model model
                     , HttpServletRequest request){

    model.addAttribute("error", error);
    model.addAttribute("exception", exception);

    return reload(request, "auth/login");
  }

  // 회원가입 페이지
  @GetMapping("/register")
  public String register(HttpServletRequest request) {
    return reload(request, "auth/register");
  }

  // 회원가입 페이지
  @GetMapping("/findId")
  public String findId(HttpServletRequest request) {
    return reload(request, "auth/findId");
  }

  // 회원가입 페이지
  @GetMapping("/findPw")
  public String findPw(HttpServletRequest request) {
    return reload(request, "auth/findPw");
  }

  // 인증 없음 페이지
  @GetMapping("/accessDenied")
  public String accessDenied() {
    return "auth/accessDenied";
  }

  private String reload(HttpServletRequest request, String defaultUri) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if(!(authentication.getPrincipal() instanceof String)) {
      return "redirect:/";
    } else {
      return defaultUri;
    }
  }

}
