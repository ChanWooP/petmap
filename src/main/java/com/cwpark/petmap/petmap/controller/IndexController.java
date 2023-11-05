package com.cwpark.petmap.petmap.controller;

import com.cwpark.petmap.petmap.service.ItemService;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
  // 인덱스 페이지
  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/notifi")
  public String notifi() {return "notifi";}

  @GetMapping("/search")
  public String search(@RequestParam String type, @RequestParam String name, @RequestParam String search, Model model) {
    model.addAttribute("type", type);
    model.addAttribute("name", name);
    model.addAttribute("search", search);
    return "search";
  }

  @GetMapping("/buy")
  public String buy(@RequestParam String user, @RequestParam String item, Model model) {
    model.addAttribute("user", user);
    model.addAttribute("item", item);

    return "buy";
  }

  @GetMapping("/pay")
  public String pay(@RequestParam String store, @RequestParam String item, @RequestParam String cnt,Model model) {

    String str = store + "&" + item + "&" + cnt;

    model.addAttribute("str", str);

    return "pay";
  }

  @GetMapping("/cart")
  public String cart() {
    return "cart";
  }
}
