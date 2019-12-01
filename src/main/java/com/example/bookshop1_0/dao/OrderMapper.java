package com.example.bookshop1_0.dao;

import com.alipay.api.domain.OrderDetail;
import com.example.bookshop1_0.entity.CartEntity;
import com.example.bookshop1_0.entity.OrderDetailEntity;
import com.example.bookshop1_0.entity.OrdersEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface OrderMapper {
    @Insert("insert into order_detail(orderId, bookId, bookname, count, price, amount, lastTime, log) " +
            "values (#{orderId}, #{bookId}, #{bookname}, #{count}, #{price}, #{amount}, #{lastTime}, #{log})")
    int insertOrdersDetail(OrderDetailEntity orderDetail);

    @Insert("insert into orders(orderId, username, amount, phone, address, createTime, status, endtime, log) " +
            "values (#{orderId}, #{username}, #{amount}, #{phone}, #{address}, #{createTime}, #{status}, #{endtime}, #{log})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId", keyColumn = "orderId")
    int insertOrders(OrdersEntity orders);

    @Select("select * from orders where username=#{username}")
    List<OrdersEntity> queryOrdersByUser(@Param("username") String username);

    @Select("select * from orders where orderId=#{orderId}")
    OrdersEntity queryOrdersById(@Param("orderId") int orderId);

    @Select("select * from order_detail where orderId=#{orderId}")
    OrderDetailEntity queryOrdersDetailById(@Param("orderId") int orderId);

    @Select("select * from orders")
    List<OrdersEntity> queryAllOrders();

    @Select("select * from order_detail")
    List<OrderDetailEntity> queryAllOrdersDetail();

    @Update("update orders set status=#{status} where orderId=#{orderId}")
    int updateStatus(@Param("status") Integer status, @Param("orderId") int orderId);

    @Delete("delete from orders where orderId=#{orderId}")
    int deleteOrders(@Param("orderId")int orderId);

    @Delete("delete from order_detail where orderId=#{orderId}")
    int deleteOrdersDetail(@Param("orderId")int orderId);

}
