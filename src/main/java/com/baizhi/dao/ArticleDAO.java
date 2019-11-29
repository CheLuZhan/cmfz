package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDAO {
    //分页查询
    public List<Article> selectAllArticle(@Param("start") Integer start, @Param("rows") Integer rows);

    //查询条数
    public Integer selectCount();

    //添加
    public void addArticle(Article article);

    //修改
    public void updateArticle(Article article);

    //查询所有文章
    public List<Article> getAll();

    //文章排序
    public List<Article> selectArticleByTime();

    //查询一片文章详细信息
    public Article seleArticleDetail(String id);
}
