package com.baizhi.service;

import com.baizhi.dao.AlbumDAO;
import com.baizhi.dao.ChapterDAO;
import com.baizhi.entity.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDAO chapterDAO;
    @Autowired
    AlbumDAO albumDAO;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> selectAllChapter(Integer page, Integer rows, String aid) {
        /*
         *
         * total:总页数
         * page:当前页数
         * records:总条数
         * rows:查询结果集合
         * */
        Integer records = chapterDAO.selectCount(aid);
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        Integer start = (page - 1) * rows;
        Map<String, Object> map = new HashMap<>();
        List<Chapter> chapters = chapterDAO.selectAllChapter(start, rows, aid);

        map.put("page", page);
        map.put("total", total);
        map.put("records", records);
        map.put("rows", chapters);
        return map;
    }

    @Override
    public void add(Chapter chapter, String aid) {
        Integer count1 = chapterDAO.selectCount(aid);
        String s = UUID.randomUUID().toString();
        Date date = new Date();
        chapter.setId(s);//id
        chapter.setUploadTime(date);
        chapter.setAlbumId(aid);
        chapterDAO.add(chapter);

        //查询chapter个数
        Integer count2 = chapterDAO.selectCount(aid);

        System.out.println("count1 = " + count1);
        System.out.println("count2 = " + count2);
        //调用album中update方法 修改number
        albumDAO.updateNumber(count2, aid);
    }

    @Override
    public void updateAudio(String newFileName, String bid, String albumId, String size, String time) {
        chapterDAO.updateAudio(newFileName, bid, albumId, size, time);
    }

    @Override
    public void updateChapter(Chapter chapter) {
        chapterDAO.updateChapter(chapter);
    }

    @Override
    public void delete(String[] ids, String aid) {
        chapterDAO.delete(ids);
        //查询chapter个数
        Integer count = chapterDAO.selectCount(aid);
        //调用album中update方法 修改number
        albumDAO.updateNumber(count, aid);
    }
}
