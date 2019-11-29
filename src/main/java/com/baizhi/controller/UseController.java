package com.baizhi.controller;

import com.baizhi.entity.Province;
import com.baizhi.entity.Use;
import com.baizhi.service.UseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/use")
@RestController
public class UseController {
    @Autowired
    UseService useService;

    //活跃度
    @RequestMapping("selectState")
    public List<Integer> selectState() {
        List<Integer> list = useService.selectState();
        return list;
    }

    //
    @RequestMapping("selectByProvince")
    public Map<String, List<Province>> selectByProvince() {
        Map<String, List<Province>> map = useService.selectByProvince();
        return map;
    }

    //添加
    @RequestMapping("insert")
    public void add(Use use) {
        useService.add(use);
    }
}
