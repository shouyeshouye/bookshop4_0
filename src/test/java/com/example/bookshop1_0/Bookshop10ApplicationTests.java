package com.example.bookshop1_0;

import com.example.bookshop1_0.dao.CartMapper;
import com.example.bookshop1_0.entity.BooksEntity;
import com.example.bookshop1_0.entity.CartEntity;
import com.example.bookshop1_0.service.bookService;
import com.example.bookshop1_0.service.userService;
import lombok.extern.log4j.Log4j2;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Log4j2
@SpringBootTest
class Bookshop10ApplicationTests {
    @Autowired
    userService userService;
    // @Autowired
    // UserMapper userMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    bookService bookService;

    @Test
    void contextLoads(){

        BooksEntity book = new BooksEntity();
        book.setId(2);
        book.setPublisher("哈哈哈");
        book.setBookname("哈哈哈");
        book.setAuthor("哈哈哈");
        bookService.delete("2");
        List<BooksEntity> lists = bookService.queryByText("哈哈哈");
        for (BooksEntity list:lists) {
            System.out.println(list.getBookname()+"--"+list.getAuthor()+"--"+list.getPublisher());
        }
    }

}
