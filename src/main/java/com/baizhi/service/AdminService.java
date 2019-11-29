package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.List;

public interface AdminService {
    //查询用户
    public List<Admin> selectName(String name);

    //查询用户和密码
    public Admin selectAdmin(String name, String password);

}
