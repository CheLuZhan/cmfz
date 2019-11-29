package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    //分页查询所有
    @RequestMapping("getAllAlbum")
    public Map<String, Object> getAllAlbum(Integer page, Integer rows) {
        Map<String, Object> map = albumService.selectAllAlbum(page, rows);
        return map;
    }

    //编辑
    @RequestMapping("edit")
    public String edit(String oper, Album album, String[] id) {
        if (oper.equals("add")) {
            albumService.add(album);
            return album.getId();
        } else if (oper.equals("del")) {
            albumService.delete(id);
        } else if (oper.equals("edit")) {
            albumService.updateAlbum(album);
        }
        return null;
    }

    //上传
    @RequestMapping("upload")
    public void upload(MultipartFile src, String bid, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/img/");
        File file = new File(realPath);
        //判断文件是否存在
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = src.getOriginalFilename();//获取文件名
        String newFileName = new Date().getTime() + "_" + filename;
        try {
            src.transferTo(new File(file, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改数据库中图片名字
        albumService.update(newFileName, bid);
    }
}














