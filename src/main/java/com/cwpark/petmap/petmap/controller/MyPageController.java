package com.cwpark.petmap.petmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/mypage")
public class MyPageController {

    @GetMapping("")
    public String mypage() {
        return "mypage/mypage";
    }

    @GetMapping("/privacy")
    public String privacy() {
        return "mypage/privacy";
    }

    @GetMapping("/coupon")
    public String coupon() {
        return "mypage/coupon";
    }

    @GetMapping("/point")
    public String point() {
        return "mypage/point";
    }

    @GetMapping("/qna")
    public String qna() {
        return "mypage/qna";
    }

    @GetMapping("/order")
    public String order() {
        return "mypage/order";
    }
}
