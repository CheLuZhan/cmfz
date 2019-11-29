package com.baizhi.service;

import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import com.baizhi.esrepository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDAO articleDAO;
    @Autowired
    ArticleRepository articleRepository;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> selectAllArticle(Integer page, Integer rows) {
        //通过当前页计算该页的起始条数
        /*
         * total:总页数
         * page:当前页数
         * records:总条数
         * rows:当前页的查询结果集
         * */
        //当前页起始条数
        Integer start = (page - 1) * rows;
        List<Article> articles = articleDAO.selectAllArticle(start, rows);
        Integer records = articleDAO.selectCount();//查询总条数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;//计算总页数

        Map<String, Object> map = new HashMap<>();//创建map集合

        map.put("records", records);//返回总条数
        map.put("total", total);     //返回总页数
        map.put("page", page);      //返回当前页数
        map.put("rows", articles);  //当前页的查询结果集
        return map;
    }


    @Override
    public void addArticle(Article article) {
        String s = UUID.randomUUID().toString();
        Date date = new Date();
        article.setId(s);
        article.setDate(date);
        articleDAO.addArticle(article);

        //入es库
        try {
            articleRepository.add(article);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateArticle(Article article) {
        Date date = new Date();
        article.setDate(date);
        articleDAO.updateArticle(article);
    }


    //es添加
    @Override
    public void add() {
        List<Article> list = articleDAO.getAll();
        for (Article article : list) {
            try {
                articleRepository.add(article);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //es查询
    public Map<String, Object> search(String query, Integer page, Integer rows) {
        Integer count = articleRepository.searchCount(query, page, rows);//查询总条数
        Integer start = (page - 1) * rows;//起始条数
        Integer total = count % page == 0 ? count / page : count / page + 1;
        //分页
        List<Map> list = articleRepository.search(query, start, rows);
        Map<String, Object> map = new HashMap<>();

        map.put("records", count);//返回总条数
        map.put("page", page);//当前页
        map.put("total", total);//总页数
        map.put("rows", list);
        return map;
    }
}
