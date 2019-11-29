package com.baizhi.service;

import com.baizhi.dao.*;
import com.baizhi.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ViewServiceImpl implements ViewService {
    @Autowired
    BannerDAO bannerDAO;
    @Autowired
    AlbumDAO albumDAO;
    @Autowired
    ArticleDAO articleDAO;
    @Autowired
    ChapterDAO chapterDAO;
    @Autowired
    AdminDAO adminDAO;

    //展示主页面
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> view(String uid, String type, String sub_type) {
        Map<String, Object> map = new HashMap<>();
        HashMap<String, Object> map2 = new HashMap<>();
        //all 查询首页
        if ("all".equals(type)) {
            //查询轮播图
            List<Banner> banners = bannerDAO.selectByTime();
            //查询专辑排序
            List<Album> albums = albumDAO.selectAlbumByTime();
            //查询文章排序
            List<Article> articles = articleDAO.selectArticleByTime();
            try {
                map2.put("articles", articles);
                map2.put("albums", albums);
                map.put("code", 200);
                map.put("header", banners);
                map.put("body", map2);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code", 500);
                map.put("msg", "参数错误");
            }
        } else if ("wen".equals(type)) {
            //查询专辑
            List<Album> albums = albumDAO.getAllAlbum();
            try {
                map2.put("albums", albums);
                map.put("code", 200);
                map.put("body", map2);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code", 500);
                map.put("msg", "参数错误");
            }
        } else if ("si".equals(type)) {
            List<Article> articles = articleDAO.getAll();
            if ("ssyj".equals(sub_type)) {//上师言教
                try {
                    map2.put("articles", articles);
                    map.put("code", 200);
                    map.put("body", map2);
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("code", 500);
                    map.put("msg", "参数错误");
                }
            } else if ("xmfy".equals(sub_type)) {
                try {
                    map2.put("articles", articles);
                    map.put("code", 200);
                    map.put("body", map2);
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("code", 500);
                    map.put("msg", "参数错误");
                }
            }
        }
        return map;
    }

    //查看文章详情
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map seleArticleDetail(String id, String uid) {
        Map<String, Object> map = new HashMap<>();
        Article article = articleDAO.seleArticleDetail(id);
        try {
            map.put("code", 200);
            map.put("link", "http://xxx/1000.html");
            map.put("id", id);
            map.put("ext", "");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", "参数错误");
        }
        return map;
    }

    //查看专辑详情
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectAlbumDetail(String id, String uid) {
        Map<String, Object> map = new HashMap<>();
        //查询专辑信息
        Album album = albumDAO.selectAlbumById(id);
        //查询音频信息
        List<Chapter> chapters = chapterDAO.selectByAlbumId(id);
        try {
            map.put("introduction", album);
            map.put("list", chapters);
            map.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", "参数错误");
        }
        return map;
    }

    //登录业务
    public Object login(String phone, String password) {
        Map<String, Object> map = new HashMap<>();
        try {
            Admin admin = adminDAO.selectAdmin(phone, password);
            return admin;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "-200");
            map.put("errmsg", "密码错误");
            return map;
        }
    }

    //注册业务
    public Object regiset(String phone, String password) {
        Map<String, Object> map = new HashMap<>();
        try {
            Admin admin = adminDAO.selectAdmin(phone, password);
            if (admin == null) {
                admin = new Admin();
                String s = UUID.randomUUID().toString();
                admin.setId(s);
                admin.setName(phone);
                admin.setPassword(password);
                adminDAO.addAdmin(admin);
                return admin;
            }
            map.put("error", "-200");
            map.put("errmsg", "改手机号已经存在");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", "-200");
            map.put("errmsg", "改手机号已经存在");
            return map;
        }
    }

    //修改
    @Override
    public Object update(String id, String name, String password) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Admin> admins = adminDAO.selectName(name);
            if (admins == null) {
                adminDAO.update(id, name, password);
                Admin admin2 = adminDAO.selectAdmin(name, password);
                return admin2;
            } else {
                return map.put("errmsg", "该手机号不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("erroe", "-200");
        }
        return null;
    }

}
