package com.example.bookshop1_0.entity;

import java.io.Serializable;
import java.util.List;

public class SysRole implements Serializable {

    private Integer id;
    private String role;
    private List<SysPermission> permissions;
    private List<SysUser> users;
    private Integer uid;

    public Integer getUid() { return uid; }

    public void setUid(Integer uid) { this.uid = uid; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermission> permissions) {
        this.permissions = permissions;
    }

    public List<SysUser> getUsers() {
        return users;
    }

    public void setUsers(List<SysUser> users) {
        this.users = users;
    }

}
