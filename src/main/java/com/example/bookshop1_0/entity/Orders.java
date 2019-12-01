package com.example.bookshop1_0.entity;

import java.util.List;

public class Orders {
    private OrdersEntity orders;
    private OrderDetailEntity orderDetail;

    public OrderDetailEntity getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailEntity orderDetail) {
        this.orderDetail = orderDetail;
    }

    public OrdersEntity getOrders() {
        return orders;
    }

    public void setOrders(OrdersEntity orders) {
        this.orders = orders;
    }
}
