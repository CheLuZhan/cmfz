package com.baizhi.service;

import com.baizhi.dao.BannerDAO;
import com.baizhi.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDAO bannerDAO;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectAllBanner(Integer page, Integer rows) {//page页面传来的当前页数 rows每页条数
        //通过当前页计算该页的起始条数
        /*
         * total:总页数
         * page:当前页数
         * records:总条数
         * rows:当前页的查询结果集
         * */
        Map<String, Object> map = new HashMap<>();//创建map集合
        //每页起始条数
        Integer start = (page - 1) * rows;
        List<Banner> products = bannerDAO.selectAllBanner(start, rows);//查询当前页展示的每一条
        Integer records = bannerDAO.selectCount();//查询总条数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;//计算总页数

        map.put("records", records);//返回总条数
        map.put("total", total);     //返回总页数
        map.put("page", page);      //返回当前页数
        map.put("rows", products);  //当前页的查询结果集
        return map;
    }

    //查询条数
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer selectCount() {
        Integer count = bannerDAO.selectCount();
        return count;
    }

    //添加
    @Override
    public void add(Banner banner) {
        String s = UUID.randomUUID().toString();//id
        Date date = new Date();
        banner.setId(s);
        banner.setState("T");
        banner.setDate(date);
        bannerDAO.add(banner);
    }

    //修改
    @Override
    public void update(Banner banner) {
        Date date = new Date();
        banner.setDate(date);
        bannerDAO.update(banner);
    }

    //修改图片
    @Override
    public void updateSrc(String newFileName, String id) {
        bannerDAO.updateSrc(newFileName, id);
    }

    //删除
    @Override
    public void delete(String[] ids) {
        bannerDAO.delete(ids);
    }

    @Override
    public List<Banner> selectAll() {
        List<Banner> list = bannerDAO.selectAll();
        return list;
    }

    //轮播图最新排序
    @Override
    public List<Banner> selectByTime() {
        List<Banner> list = bannerDAO.selectByTime();
        return list;
    }

}
