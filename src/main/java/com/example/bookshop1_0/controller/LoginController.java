package com.example.bookshop1_0.controller;


import com.example.bookshop1_0.filter.KickoutSessionControlFilter;
import com.example.bookshop1_0.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;


@Log4j2
@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    KickoutSessionControlFilter kickoutFilter;


    @RequestMapping("/")
    public String index(Model model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            String username = (String) subject.getPrincipals().getPrimaryPrincipal();
            log.info(username + "---------------------------------------");
            model.addAttribute("username", username);
            return "index";
        }
        return "index";
    }

    @RequestMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/kickout")
    public String kickout() {
        return "你被T了";
    }

    @RequestMapping("/isLogin")
    public String isLogin() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "已经登录";
        }
        return "还未登录";
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        Serializable sessionid= subject.getSession().getId();
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        subject.logout();
        Cache<String, Deque<Serializable>> cache = kickoutFilter.getCache();
        Deque<Serializable> deque = cache.get(username);
        deque.remove(sessionid);
        cache.put(username, deque);
        return "redirect:/";
    }

    @RequestMapping("/doLogin")
    public String doLogin(Model model, @RequestParam(value = "Username") String username,
                          @RequestParam(value = "Password") String password) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Serializable sessionId = session.getId();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            log.info(subject.hasRole("admin") + "++++++++++++++++++++++++++++++");
            if (subject.isAuthenticated()) {
                if (subject.hasRole("admin") == true) {
                    return "redirect:/admin/storehouse";
                }
                return "redirect:/";
            }

        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("message", "密码错误");
            return "login";
        } catch (UnknownAccountException uae) {
            model.addAttribute("message", "用户名错误");
            return "login";
        }
        Cache<String, Deque<Serializable>> cache = kickoutFilter.getCache();
        Deque<Serializable> deque = cache.get(username);
        if (deque == null) {
            // 初始化队列
            deque = new ArrayDeque<Serializable>();
        }
        // 如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId)
                && session.getAttribute("kickout") == null) {
            // 将sessionId存入队列
            deque.push(sessionId);
            // 将用户的sessionId队列缓存
            cache.put(username, deque);
        }
        return "redirect:/";
    }

    @RequestMapping("/notRole")
    public String unauthc() {
        return "404";
    }

    @RequestMapping("/admin/act")
    public String userAct() {
        return "adminAct";
    }
}
