package com.baizhi.service;

import com.baizhi.dao.AdminDAO;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDAO adminDAO;

    //跟据名字查询
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Admin> selectName(String name) {
        List<Admin> admins = adminDAO.selectName(name);
        return admins;
    }

    //根据名字密码查询
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Admin selectAdmin(String name, String password) {
        Admin admin = adminDAO.selectAdmin(name, password);
        return admin;
    }
}
