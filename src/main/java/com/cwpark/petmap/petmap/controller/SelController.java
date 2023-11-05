package com.cwpark.petmap.petmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/sel")
public class SelController {
    @GetMapping("")
    public String admin() {
        return "sel/sel";
    }

    @GetMapping("/item")
    public String coupon() {
        return "sel/item";
    }

    @GetMapping("/stock")
    public String stock() {
        return "sel/stock";
    }

    @GetMapping("/qna")
    public String qna() {
        return "sel/qna";
    }

    @GetMapping("/order")
    public String order() {
        return "sel/order";
    }

    @GetMapping("/sale")
    public String sale() {
        return "sel/sale";
    }
}