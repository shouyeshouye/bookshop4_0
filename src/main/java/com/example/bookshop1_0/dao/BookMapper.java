package com.example.bookshop1_0.dao;

import com.example.bookshop1_0.entity.BooksEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookMapper {
    @Select("select * from books")
    List<BooksEntity> queryAll();

    @Select("select * from books where categoryMain=#{categoryMain}")
    List<BooksEntity> queryByCategoryMain(@Param("categoryMain")String categoryMain);

    @Select("select * from books where categorySmall=#{categorySmall}")
    List<BooksEntity> queryByCategorySmall(@Param("categorySmall")String categorySmall);
}
