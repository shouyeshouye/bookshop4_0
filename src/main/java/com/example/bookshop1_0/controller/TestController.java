package com.example.bookshop1_0.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/新用户注册")
    public String OrderRegister(){
        return "register";
    }

    @RequestMapping("/cart")
    public String Orderdd(){
        return "dd";
    }

    @RequestMapping("/dd_pay")
    public String Orderdd_pay(){
        return "dd_pay";
    }

    @RequestMapping("/dd_success")
    public String Orderdd_success(){
        return "dd_success";
    }

    @RequestMapping("/dd_test")
    public String Orderdd_dd_test(){
        return "cart";
    }


}
