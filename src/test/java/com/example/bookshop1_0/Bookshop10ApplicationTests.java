package com.example.bookshop1_0;

import com.example.bookshop1_0.dao.BookMapper;
import com.example.bookshop1_0.entity.CartEntity;
import com.example.bookshop1_0.entity.SysUser;
import com.example.bookshop1_0.service.BookService;
import com.example.bookshop1_0.service.CartService;
import com.example.bookshop1_0.service.OrderService;
import com.example.bookshop1_0.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@SpringBootTest
class Bookshop10ApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    BookService bookService;
    @Autowired
    CartService cartService;

    @Test
    void contextLoads() {
        SysUser user=new SysUser();
        user.setUsername("admin");
        user.setPassword("admin");
        System.out.println(userService.addAdmin(user));

    }

}
