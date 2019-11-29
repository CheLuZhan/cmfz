package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    //分页查询
    @RequestMapping("getAllArticle")
    public Map<String, Object> getAllArticle(Integer page, Integer rows) {
        Map<String, Object> map = articleService.selectAllArticle(page, rows);
        return map;
    }

    //添加一个
    @RequestMapping("addArticle")
    public void addArticle(Article article) {
        articleService.addArticle(article);
    }

    //修改
    @RequestMapping("updateArticle")
    public void updateArticle(Article article) {
        articleService.updateArticle(article);
    }

    //es 添加多个
    @RequestMapping("add")
    public void add() {
        articleService.add();
    }

    //es多字段分词查询
    @RequestMapping("search")
    public Map<String, Object> search(String query, Integer page, Integer rows) {
        Map<String, Object> map = articleService.search(query, page, rows);
        return map;
    }
}
