package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    //分页查询所有图片
    public Map<String, Object> selectAllBanner(Integer page, Integer rows);

    //查询所有条数
    public Integer selectCount();

    //添加
    public void add(Banner banner);

    //修改
    public void update(Banner banner);

    //修改图片
    public void updateSrc(String newFileName, String id);

    //删除
    public void delete(String[] ids);

    //查询所有
    public List<Banner> selectAll();

    //查询最新的轮播图
    public List<Banner> selectByTime();
}
