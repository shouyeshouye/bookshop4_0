package com.example.bookshop1_0.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cart")
public class Cartcontroller {
    @RequestMapping("cart/add")
    public void add(@RequestParam(value = "bookid") String bookid,
                    @RequestParam(value = "count") String count) {

    }
}
