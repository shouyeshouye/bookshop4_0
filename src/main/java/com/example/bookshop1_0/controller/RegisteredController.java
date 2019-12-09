package com.example.bookshop1_0.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller

public class RegisteredController {
    @RequestMapping("/registered")
    public String DoRegistered(){
        return "register";
    }
}
