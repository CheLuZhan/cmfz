package com.baizhi.service;

import com.baizhi.dao.AlbumDAO;
import com.baizhi.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDAO albumDAO;

    //分页查询
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> selectAllAlbum(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        /*
         * start:每页起始条数
         * rows:每页展示条数
         *
         * total:总页数
         * page:当前页
         * records:总条数
         * rows  查询集合
         * */
        Integer start = (page - 1) * rows;//当前页起始条数
        Integer records = albumDAO.selectCount();//总条数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;//总页数
        List<Album> albums = albumDAO.selectAllAlbum(start, rows);//当前页查询结果集

        map.put("total", total);
        map.put("page", page);
        map.put("records", records);
        map.put("rows", albums);
        return map;
    }

    //添加
    @Override
    public void add(Album album) {
        String s = UUID.randomUUID().toString();
        Date date = new Date();

        album.setNumber(0);
        album.setId(s);
        album.setUploadTime(date);
        albumDAO.add(album);
    }

    //修改图片
    @Override
    public void update(String newFileName, String id) {
        albumDAO.update(newFileName, id);
    }

    //批量删除图片
    @Override
    public void delete(String[] ids) {
        albumDAO.delete(ids);
    }

    //修改图片
    @Override
    public void updateAlbum(Album album) {
        albumDAO.updateAlbum(album);
    }


}
