package com.example.bookshop1_0.dao;

import com.example.bookshop1_0.entity.BooksEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jdk.nashorn.internal.runtime.PropertyDescriptor.SET;

@Mapper
@Repository
public interface BookMapper {
    @Select("select * from books")
    List<BooksEntity> queryAll();

    @Select("select * from books where categoryMain=#{categoryMain}")
    List<BooksEntity> queryByCategoryMain(@Param("categoryMain")String categoryMain);

    @Select("select * from books where categorySmall=#{categorySmall}")
    List<BooksEntity> queryByCategorySmall(@Param("categorySmall")String categorySmall);

    @Select("select * from books where id=#{id} for update")
    BooksEntity queryById(@Param("id")int id);

    @Insert("insert into books(categoryMain, categorySmall, bookname, author, publisher, " +
            "publishtime, price, stock, sale, imgPath, description, lastTime, log, version) " +
            "values (#{categoryMain}, #{categorySmall}, #{bookname}, #{author}, #{publisher}, " +
            "#{publishtime}, #{price}, #{stock}), #{sale}, #{imgPath}, #{description}, #{lastTime}, " +
            "#{log}, #{version}")
    void add(BooksEntity book);

    @Update("update books set stock = #{book.stock}, sale = #{book.sale}, version = #{book.version}+1 where id = #{book.id} and version = #{updateVersion};")
    int updateBooksCountOptimisticLock(@Param("book") BooksEntity book, @Param("updateVersion") int version);

    @Update({
            "<script>",
            "update books set",
            "<if test='book.categoryMain!=null'>", " categoryMain = #{book.categoryMain}, ", "</if>",
            "<if test='book.categorySmall!=null'>", " categorySmall = #{book.categorySmall}, ", "</if>",
            "<if test='book.bookname!=null'>", " bookname = #{book.bookname}, ", "</if>",
            "<if test='book.author!=null'>", " author = #{book.author}, ", "</if>",
            "<if test='book.publisher!=null'>", " publisher = #{book.publisher}, ", "</if>",
            "<if test='book.price!=null'>", " price = #{book.price}, ", "</if>",
            "<if test='book.stock!=null'>", " stock = #{book.stock}, ", "</if>",
            "id=id where id = #{book.id}",
            "</script>"
    })
    int update(@Param("book")BooksEntity book);


    }
