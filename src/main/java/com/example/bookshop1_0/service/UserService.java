package com.example.bookshop1_0.service;

import com.example.bookshop1_0.dao.RoleMapper;
import com.example.bookshop1_0.dao.UserMapper;
import com.example.bookshop1_0.entity.SysRole;
import com.example.bookshop1_0.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;

    public SysUser findUserByName(String name) {
        SysUser user = userMapper.findByUserName(name);
        List<SysRole> roleList = roleMapper.findByUid(user.getId());
        user.setRoles(roleList);
        return user;
    }
}
