package com.example.bookshop1_0.service;

import com.example.bookshop1_0.dao.OrderMapper;
import com.example.bookshop1_0.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;

    public int insertOrder(OrdersEntity orders, OrderDetailEntity orderDetail) {
        orderMapper.insertOrders(orders);
        OrdersEntity order = orderMapper.queryOrdersById(orders.getOrderId());
        if (order != null) {
            orderDetail.setOrderId(order.getOrderId());
            if (orderMapper.insertOrdersDetail(orderDetail) > 0)
                return 1;
        }
        return 0;
    }

    public int updateStatus(Integer status, int orderId) {
        return orderMapper.updateStatus(status, orderId);
    }

    public int deleteOrder(int orderId) {
        orderMapper.deleteOrdersDetail(orderId);
        if (orderMapper.queryOrdersDetailById(orderId) == null) {
            if (orderMapper.deleteOrders(orderId) > 0)
                return 1;
        }
        return 0;
    }

    public List<Orders> queryOrderByUser(String username) {
        List<Orders> orders = new ArrayList<>();
        List<OrdersEntity> lists = orderMapper.queryOrdersByUser(username);
        for (OrdersEntity list : lists) {
            Orders order = new Orders();
            OrderDetailEntity orderDetail = orderMapper.queryOrdersDetailById(list.getOrderId());
            order.setOrders(list);
            order.setOrderDetail(orderDetail);
            orders.add(order);
        }
        return orders;
    }

    public int orderWithDirectly(int bookId, int count, String usnername) {

        BooksEntity book = bookService.queryById(bookId);
        if (null != book && book.getStock() < count || book == null) {
            return 0;
        }
        int currentVersion = book.getVersion();
        BooksEntity bookForUpdate = new BooksEntity();
        bookForUpdate.setVersion(currentVersion);
        bookForUpdate.setStock(book.getStock() - count);
        bookForUpdate.setSale(book.getSale() + count);
        bookForUpdate.setId(bookId);
        int i = bookService.updateBookCountOptimisticLock(bookForUpdate, currentVersion);
        if (i > 0) {
            OrdersEntity orders = new OrdersEntity();
            OrderDetailEntity orderDetail = new OrderDetailEntity();
            SysUser user = userService.findUserByName(usnername);
            if (user != null) {
                orders.setUsername(user.getUsername());
                orders.setAddress(user.getAddress());
                orders.setPhone(user.getPhone());
                orders.setAmount(book.getPrice() * count);
                orders.setStatus(0);
                orders.setCreateTime(new Date());

                orderDetail.setBookId(book.getId());
                orderDetail.setAmount(book.getPrice() * count);
                orderDetail.setBookname(book.getBookname());
                orderDetail.setCount(count);
                orderDetail.setPrice(book.getPrice());
                if (insertOrder(orders, orderDetail) > 0)
                    return 1;
            }
        }
        return 0;
    }

    public int orderWithCart(List<Integer> cartId, String username) {
        int sucess = 0;
        for (Integer id : cartId) {
            CartEntity cart = cartService.queryByIdandUserneme(id, username);
            if (cart != null) {
                orderWithDirectly(cart.getBookId(), cart.getQuantity(), username);
                cartService.deleteById(cart.getId());
                sucess++;
            }
        }
        return sucess;
    }

}
