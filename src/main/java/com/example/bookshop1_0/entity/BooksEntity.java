package com.example.bookshop1_0.entity;


import java.util.Date;
import java.util.Objects;


public class BooksEntity {
    private int id;
    private String categoryMain;
    private String categorySmall;
    private String bookname;
    private String author;
    private String publisher;
    private String publishtime;
    private Double price;
    private Integer stock;
    private Integer sale;
    private String imgPath;
    private String description;
    private Date lastTime;
    private String log;
    private Integer version;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getVersion() { return version; }

    public void setVersion(Integer version) { this.version = version; }

    public void setSale(Integer sale) { this.sale = sale; }

    public Integer getSale() { return sale; }

    public String getCategoryMain() {
        return categoryMain;
    }

    public void setCategoryMain(String categoryMain) {
        this.categoryMain = categoryMain;
    }


    public String getCategorySmall() {
        return categorySmall;
    }

    public void setCategorySmall(String categorySmall) {
        this.categorySmall = categorySmall;
    }


    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }


    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
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
        BooksEntity that = (BooksEntity) o;
        return id == that.id &&
                Objects.equals(categoryMain, that.categoryMain) &&
                Objects.equals(categorySmall, that.categorySmall) &&
                Objects.equals(bookname, that.bookname) &&
                Objects.equals(author, that.author) &&
                Objects.equals(publisher, that.publisher) &&
                Objects.equals(publishtime, that.publishtime) &&
                Objects.equals(price, that.price) &&
                Objects.equals(stock, that.stock) &&
                Objects.equals(imgPath, that.imgPath) &&
                Objects.equals(description, that.description) &&
                Objects.equals(lastTime, that.lastTime) &&
                Objects.equals(log, that.log);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryMain, categorySmall, bookname, author, publisher, publishtime, price, stock, imgPath, description, lastTime, log);
    }
}
