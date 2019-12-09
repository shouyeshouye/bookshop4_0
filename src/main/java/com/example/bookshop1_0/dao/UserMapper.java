package com.example.bookshop1_0.dao;

import com.example.bookshop1_0.entity.SysUser;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
@Mapper
@Repository
public interface UserMapper extends Serializable {

    @Select("select * from user_t where username = #{username,jdbcType=VARCHAR}")
    SysUser findByUserName(@Param("username")String username);
    @Insert("insert into user_t(password, salt, username) values(#{user.password}, #{user.salt}, #{user.username})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addUser(@Param("user")SysUser user);
}
