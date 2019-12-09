package com.example.bookshop1_0.service;

import com.example.bookshop1_0.dao.CartMapper;
import com.example.bookshop1_0.entity.BooksEntity;
import com.example.bookshop1_0.entity.CartEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    BookService bookService;
    public int insert(String username, int bookid, int count){
        BooksEntity book = bookService.queryById(bookid);
        if (book!=null){
            CartEntity cart=new CartEntity();
            cart.setBookId(book.getId());
            cart.setUserName(username);
            cart.setPrice(book.getPrice());
            cart.setQuantity(count);
            cart.setAmount(count*book.getPrice());
            cart.setCreateTime(new Date());
            return cartMapper.insert(cart);

        }
        return 0;
    }

    public int updateCount(int quantity, int id){
        return cartMapper.updateCount(quantity,id);
    }


    public int clearCart(String userName){
        return cartMapper.clearCart(userName);
    }

    public int deleteById(int id){
        return cartMapper.deleteById(id);
    }

    public List<CartEntity> queryAll(){
        return cartMapper.queryAll();
    }


    public CartEntity queryById(int id){
        return cartMapper.queryById(id);
    }

    public List<CartEntity> queryByUserneme(String username){
        return cartMapper.queryByUserneme(username);
    }
}
