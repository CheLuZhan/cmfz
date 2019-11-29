package com.baizhi.service;

import java.util.Map;

public interface ViewService {
    //页面
    public Map<String, Object> view(String uid, String type, String sub_type);

    //查看文章详情
    public Map seleArticleDetail(String id, String uid);

    //查看专辑详情
    public Map<String, Object> selectAlbumDetail(String id, String uid);

    //登录
    public Object login(String phone, String password);

    //注册业务
    public Object regiset(String phone, String password);

    //修改
    public Object update(String id, String name, String password);
}
