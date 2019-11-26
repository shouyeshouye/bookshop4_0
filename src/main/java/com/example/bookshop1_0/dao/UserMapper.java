package com.example.bookshop1_0.dao;

import com.example.bookshop1_0.entity.SysUser;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
@Mapper
@Repository
public interface UserMapper extends Serializable {

    @Select("select * from user_t where username = #{username,jdbcType=VARCHAR}")
    SysUser findByUserName(@Param("username")String username);
}
