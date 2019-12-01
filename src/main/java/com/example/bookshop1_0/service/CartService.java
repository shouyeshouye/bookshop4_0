package com.example.bookshop1_0.service;

import com.example.bookshop1_0.dao.CartMapper;
import com.example.bookshop1_0.entity.CartEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartMapper cartMapper;

    public int insert(CartEntity cart){
        return cartMapper.insert(cart);
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

    public CartEntity queryByIdandUserneme(int id, String username){
        return cartMapper.queryByIdandUserneme(id,username);
    }
}
