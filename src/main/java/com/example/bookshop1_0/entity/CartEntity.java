package com.example.bookshop1_0.entity;

import java.util.Date;
import java.util.Objects;

public class CartEntity {
    private int id;
    private String userName;
    private Integer bookId;
    private Integer quantity;
    private Double price;
    private Double amount;
    private Date createTime;
    private Date updateTime;
    private String log;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        CartEntity that = (CartEntity) o;
        return id == that.id &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(bookId, that.bookId) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(price, that.price) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(log, that.log);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, bookId, quantity, price, amount, createTime, updateTime, log);
    }
}
