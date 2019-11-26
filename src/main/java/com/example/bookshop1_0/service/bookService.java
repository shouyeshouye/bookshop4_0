package com.example.bookshop1_0.service;

import com.example.bookshop1_0.Lucene.LuceneBean;
import com.example.bookshop1_0.dao.BookMapper;
import com.example.bookshop1_0.entity.BooksEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class bookService {
    @Autowired
    BookMapper bookMapper;
    @Autowired
    LuceneBean luceneBean;
    @Autowired
    LuceneService luceneService;

    public List<BooksEntity> queryByText(String text) {
        List<BooksEntity> books = luceneService.query(text);
        return books;
    }
    public void add(BooksEntity book){
        luceneService.add(book);
    }
    public void update(String bookId, BooksEntity book){
        luceneService.update(bookId,book);
    }
    public void delete(String bookId){
        luceneService.delete(bookId);
    }
}
