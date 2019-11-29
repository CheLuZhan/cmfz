package com.baizhi.controller;

import com.baizhi.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ViewController {
    @Autowired
    ViewService viewService;

    //一级页面接口
    @RequestMapping("first_page")
    public Map<String, Object> view(String uid, String type, String sub_type) {
        Map<String, Object> map = viewService.view(uid, type, sub_type);
        return map;
    }
}
