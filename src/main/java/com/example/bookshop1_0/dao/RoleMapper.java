package com.example.bookshop1_0.dao;


import com.example.bookshop1_0.entity.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Mapper
@Repository
public interface RoleMapper extends Serializable {
    @Select("select * from role_t where uid = #{uid,jdbcType=INTEGER}")
    List<SysRole> findByUid(@Param("uid")Integer uid);
    @Insert("insert into role_t(role, uid) values(#{role.role}, #{role.uid})")
    int addRole(@Param("role")SysRole role);
}
