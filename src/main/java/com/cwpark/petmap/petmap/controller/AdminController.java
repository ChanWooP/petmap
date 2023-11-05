package com.cwpark.petmap.petmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
    @GetMapping("")
    public String admin() {
        return "admin/admin";
    }

    @GetMapping("/coupon")
    public String coupon() {
        return "admin/coupon";
    }

    @GetMapping("/account")
    public String account() { return "admin/account"; }

    @GetMapping("/qna")
    public String qna() {
        return "admin/qna";
    }

    @GetMapping("/category")
    public String category() {
        return "admin/category";
    }
}