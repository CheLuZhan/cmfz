package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDAO {
    //分页查询所有商品
    public List<Banner> selectAllBanner(@Param("start") Integer start, @Param("rows") Integer rows);

    //查询所有条数
    public Integer selectCount();

    //添加
    public void add(Banner banner);

    //修改
    public void update(Banner banner);

    //修改图片
    public void updateSrc(@Param("newFileName") String newFileName, @Param("id") String id);

    //删除
    public void delete(String[] ids);

    //查询所有
    public List<Banner> selectAll();

    //查询最新的轮播图
    public List<Banner> selectByTime();
}
