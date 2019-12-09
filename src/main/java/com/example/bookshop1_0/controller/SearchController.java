package com.example.bookshop1_0.controller;

import com.example.bookshop1_0.entity.BooksEntity;
import com.example.bookshop1_0.service.BookService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;

@Log4j2
@Controller
public class SearchController {

    @Autowired
    BookService bookService;

    @RequestMapping("search/categoryMain")
    public String SearchbyMain(@RequestParam("categoryMain") String categoryMain, Model model) {
        Subject subject = SecurityUtils.getSubject();
        List<BooksEntity> list = bookService.queryBycategoryMain(categoryMain);
        HashSet hashSet=new HashSet();
        for (BooksEntity item:list) {
            hashSet.add(item.getCategorySmall());
        }
        model.addAttribute("BookList", list);
        model.addAttribute("HashSet",hashSet);
        if (subject.isAuthenticated()) {
            String username = (String) subject.getPrincipals().getPrimaryPrincipal();
            model.addAttribute("username", username);
            return "SearchbyMain";
        }
        return "SearchbyMain";
    }

    @RequestMapping("search/categorySmall")
    public String SearchbySmall(@RequestParam("categorySmall") String categorySmall, Model model) {
        Subject subject = SecurityUtils.getSubject();
        List<BooksEntity> list = bookService.queryBycategorySmall(categorySmall);
        List<BooksEntity> list2 = bookService.queryBycategoryMain(list.get(0).getCategoryMain());
        HashSet small=new HashSet();
        for (BooksEntity item:list2) {
            small.add(item.getCategorySmall());
        }
        model.addAttribute("BookList", list);
        model.addAttribute("categorySmall",small);
        if (subject.isAuthenticated()) {
            String username = (String) subject.getPrincipals().getPrimaryPrincipal();
            model.addAttribute("username", username);
            return "SearchbySmall";
        }
        return "SearchbySmall";
    }

    @RequestMapping("book")
    public String BookDetail(@RequestParam("bookId") String bookId, Model model,
                             @RequestParam(value = "message", required = false)String message) {
        int bookid = Integer.parseInt(bookId);
        BooksEntity booksEntity = bookService.queryById(bookid);
        model.addAttribute("booksEntity", booksEntity);
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            String username = (String) subject.getPrincipals().getPrimaryPrincipal();
            if(message!=null){
                model.addAttribute("message", message);
            }
            model.addAttribute("username", username);
            return "product";
        }
        return "product";

    }


    @RequestMapping("/find/key")
    public String FindReturn(@RequestParam("key") String key, Model model) {
        model.addAttribute("key", key);
        return "test";
    }

}
