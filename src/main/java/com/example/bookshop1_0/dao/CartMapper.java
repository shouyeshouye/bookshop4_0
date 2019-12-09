package com.example.bookshop1_0.dao;

import com.example.bookshop1_0.entity.CartEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CartMapper {
    @Insert("insert into cart(userName, bookId, quantity, price, amount, createTime, updateTime, log) " +
            "values (#{userName}, #{bookId}, #{quantity}, #{price}, #{quantity}*#{price}, #{createTime}, #{updateTime}, #{log})")
    int insert(CartEntity cart);

    @Update("update cart set quantity=#{quantity}, amount=#{quantity}*price where id=#{id}")
    int updateCount(@Param("quantity") int quantity, @Param("id") int id);

    @Delete("delete from cart where userName=#{userName}")
    int clearCart(@Param("userName")String userName);

    @Delete("delete from cart where id=#{id}")
    int deleteById(@Param("id")int id);

    @Select("select * from cart")
    List<CartEntity> queryAll();

    @Select("select * from cart where id=#{id}")
    CartEntity queryById(@Param("id")int id);

    @Select("select * from cart where userName=#{userName}")
    List<CartEntity> queryByUserneme( @Param("userName") String username);

}
