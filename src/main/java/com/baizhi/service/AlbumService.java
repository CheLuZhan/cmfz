package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    //分页查询所有专辑
    public Map<String, Object> selectAllAlbum(Integer start, Integer rows);

    //添加
    public void add(Album album);

    //修改图片
    public void update(String newFileName, String id);

    //批量删除图片
    public void delete(String[] ids);

    //修改图片
    public void updateAlbum(Album album);
}
