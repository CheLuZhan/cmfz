package com.baizhi.dao;

import com.baizhi.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminDAO {
    //查询用户名
    public List<Admin> selectName(String name);

    //查询用户
    public Admin selectAdmin(@Param("name") String name, @Param("password") String password);

    //注册用户
    public void addAdmin(Admin admin);

    public void update(@Param("id") String id, @Param("name") String name, @Param("password") String password);

}
