package com.example.bookshop1_0.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class OrdersEntity {
    private int orderId;
    private String username;
    private Double amount;
    private String phone;
    private String address;
    private Timestamp createTime;
    private String status;
    private Timestamp endtime;
    private String log;


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return orderId == that.orderId &&
                Objects.equals(username, that.username) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(address, that.address) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(status, that.status) &&
                Objects.equals(endtime, that.endtime) &&
                Objects.equals(log, that.log);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, username, amount, phone, address, createTime, status, endtime, log);
    }
}
