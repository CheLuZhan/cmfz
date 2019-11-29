package com.baizhi.controller;

import com.baizhi.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/account")
@RestController
public class AccountController {
    @Autowired
    ViewService viewService;

    //登录
    @RequestMapping("login")
    public Object login(String phone, String password) {
        //调用登录业务
        Object login = viewService.login(phone, password);
        return login;
    }

    //注册
    @RequestMapping("regist")
    public Object regiset(String phone, String password) {
        Object regiset = viewService.regiset(phone, password);
        return regiset;
    }

    //修改
    @RequestMapping("update")
    public Object update(String id, String name, String password) {
        Object update = viewService.update(id, name, password);
        return update;
    }
}
