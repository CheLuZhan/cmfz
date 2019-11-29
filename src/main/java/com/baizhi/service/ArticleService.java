package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {
    //分页查询
    public Map<String, Object> selectAllArticle(Integer page, Integer rows);

    //添加
    public void addArticle(Article article);

    //修改
    public void updateArticle(Article article);

    //es中添加
    public void add();

    //es查询
    public Map<String, Object> search(String query, Integer page, Integer rows);
}
