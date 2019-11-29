package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDAO {
    //分页查询所有专辑
    public List<Album> selectAllAlbum(@Param("start") Integer start, @Param("rows") Integer rows);

    //查询总条数
    public Integer selectCount();

    //添加
    public void add(Album album);

    //修改图片
    public void update(@Param("newFileName") String newFileName, @Param("id") String id);

    //批量删除图片
    public void delete(String[] ids);

    //修改专辑
    public void updateAlbum(Album album);

    //修改number
    public void updateNumber(@Param("number") Integer number, @Param("id") String id);

    //专辑排序
    public List<Album> selectAlbumByTime();

    //查询所有专辑
    public List<Album> getAllAlbum();

    //查询专辑
    public Album selectAlbumById(String id);
}
