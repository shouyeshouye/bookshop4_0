package com.example.bookshop1_0.controller;


import com.example.bookshop1_0.entity.CartEntity;
import com.example.bookshop1_0.entity.Orders;
import com.example.bookshop1_0.entity.SysUser;
import com.example.bookshop1_0.service.CartService;
import com.example.bookshop1_0.service.OrderService;
import com.example.bookshop1_0.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
public class HomeController {
    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

    @RequestMapping("user/home")
    public String home(Model model) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        model.addAttribute("username", username);
        return "member";

    }

    @RequestMapping("user/userInfo")
    public String userInfo(Model model) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        model.addAttribute("username", username);
        return "password";

    }

    @ResponseBody
    @RequestMapping("user/addcart")
    public String add(@RequestParam(value = "bookid") String bookid,
                      @RequestParam(value = "count") String count) {
        log.info("++++++++++++++++++++++++++++++++++++++++++++++" + bookid + count);
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        JsonObject jsonObject = new JsonObject();
        if (cartService.insert(username, Integer.parseInt(bookid), Integer.valueOf(count)) > 0) {
            jsonObject.addProperty("error", "0");
            log.info(jsonObject.toString());
            return jsonObject.toString();
        }
        jsonObject.addProperty("error", "1");
        log.info(jsonObject.toString());
        return jsonObject.toString();
    }

    @RequestMapping("user/mycart")
    public String mycart(Model model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            String username = (String) subject.getPrincipals().getPrimaryPrincipal();
            model.addAttribute("username", username);
            List<CartEntity> list=cartService.queryByUserneme(username);
            if(list!=null){
                model.addAttribute("cartlist",list);
            }
            return "cart";
        }
        return "cart";

    }

    @RequestMapping("user/buy")
    public String buy(@RequestParam(value = "bookid") String bookid,
                      @RequestParam(value = "count") String count, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        Subject subject = SecurityUtils.getSubject();
        String url = request.getHeader("referer");
        log.info(url + "--------------------------------");
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        SysUser user = userService.findUserByName(username);
        log.info(user.getAddress() + user.getPhone());
        if (user.getAddress() != null && user.getPhone() != null
                && !user.getAddress().equals("") && !user.getPhone().equals("")) {
            Orders orders = orderService.orderWithDirectly(Integer.valueOf(bookid), Integer.valueOf(count), username);
            if (orders != null) {
                List<Orders> list=new ArrayList<>();
                list.add(orders);
                model.addAttribute("orders", list);
                model.addAttribute("amount", orders.getOrderDetail().getAmount());
                return "dd_pay";
            }
            String message = new String("Add order error");
            return "redirect:" + url + "&message=" + message;
        }
        String message = new String("Please fill in the address and contact information");
        return "redirect:" + url + "&message=" + message;
    }

    @RequestMapping("admin/storehouse")
    public String adminhome() {
        return "storehouse";
    }

    @RequestMapping("admin/order")
    public String adminorder() {
        return "order";
    }

}
