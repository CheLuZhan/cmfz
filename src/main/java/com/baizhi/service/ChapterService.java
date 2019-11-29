package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    //分页查询
    public Map<String, Object> selectAllChapter(Integer start, Integer rows, String aid);

    //添加
    public void add(Chapter chapter, String aid);

    //修改音频名
    public void updateAudio(String newFileName, String bid, String albumId, String size, String time);

    //修改文件
    public void updateChapter(Chapter chapter);

    //批量删除
    public void delete(String[] ids, String aid);
}
