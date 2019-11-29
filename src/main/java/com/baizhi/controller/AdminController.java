package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    //后台：管理员登录查询名字
    @RequestMapping("selectName")
    public String selectName(String name) {
        List<Admin> admins = adminService.selectName(name);
        if (admins.size() > 0) {
            return "ok";
        } else {
            return "用户不存在";
        }
    }

    //后台：管理员登录
    @RequestMapping("loginAdmin")
    public String loginAdmin(String name, String password, String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String codeFirst = (String) session.getAttribute("code");
        //判断验证码是否一致
        if (codeFirst.equals(code)) {
            //验证码正确
            Admin admin = adminService.selectAdmin(name, password);
            //非空判断
            if (admin != null) {
                return "success";
            }
            return "密码错误";
        }
        return "验证码错误";
    }
}
