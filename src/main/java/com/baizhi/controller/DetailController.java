package com.baizhi.controller;

import com.baizhi.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("detail")
public class DetailController {
    @Autowired
    ViewService viewService;

    //思的详情页接口
    @RequestMapping("si")
    public Map<String, Object> detail(String id, String uid) {
        Map map = viewService.seleArticleDetail(id, uid);
        return map;
    }

    @RequestMapping("wen")
    public Map<String, Object> wen(String id, String uid) {
        Map<String, Object> map = viewService.selectAlbumDetail(id, uid);
        return map;
    }
}
