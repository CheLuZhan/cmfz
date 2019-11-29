package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDAO {
    //分页查询
    public List<Chapter> selectAllChapter(@Param("start") Integer start, @Param("rows") Integer rows, @Param("aid") String aid);

    //查询条数
    public Integer selectCount(String aid);

    //添加
    public void add(Chapter chapter);

    //修改音频名
    public void updateAudio(@Param("newFileName") String newFileName, @Param("bid") String bid, @Param("albumId") String albumId, @Param("size") String size, @Param("time") String time);

    //修改文件
    public void updateChapter(Chapter chapter);

    //批量删除
    public void delete(String[] ids);

    //专辑id查询音频
    public List<Chapter> selectByAlbumId(String albumId);
}
