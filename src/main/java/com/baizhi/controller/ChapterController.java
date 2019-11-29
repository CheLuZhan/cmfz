package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    //分页查询
    @RequestMapping("getAllChapter")
    public Map<String, Object> getAllChapter(Integer page, Integer rows, String aid) {
        Map<String, Object> map = chapterService.selectAllChapter(page, rows, aid);
        return map;
    }

    //编辑
    @RequestMapping("edit")
    public String edit(String oper, Chapter chapter, String[] id, String aid) {
        if (oper.equals("add")) {
            chapterService.add(chapter, aid);
            return chapter.getId();
        } else if (oper.equals("edit")) {
            chapterService.updateChapter(chapter);
        } else if (oper.equals("del")) {
            chapterService.delete(id, aid);
        }
        return null;
    }

    //上传音频文件
    @RequestMapping("upload")
    public void upload(MultipartFile music, String bid, String albumId, HttpSession session) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        String realPath = session.getServletContext().getRealPath("/audio/");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = music.getOriginalFilename();//获取文件名
        String newFileName = new Date().getTime() + "_" + filename;
        try {
            music.transferTo(new File(file, newFileName));//拷貝文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改size，time
        long l = music.getSize();
        String size = l / 1024 / 1024 + "MB"; //size
        AudioFile read = AudioFileIO.read(new File(realPath, newFileName));
        AudioHeader audioHeader = read.getAudioHeader();//文件详细信息
        int length = audioHeader.getTrackLength();//获取秒数
        String m = length / 60 + "分";
        String s = length % 60 + "秒";
        String time = m + s;
        //修改数据库的路径和albumId
        chapterService.updateAudio(newFileName, bid, albumId, size, time);
    }

    //下载文件
    @RequestMapping("down")
    public void down(String music, HttpSession session, HttpServletResponse response) throws IOException {
        String realPath = session.getServletContext().getRealPath("audio");
        File file = new File(realPath, music);
        //获取文件响应类型
        String extension = FilenameUtils.getExtension(music);
        String mimeType = session.getServletContext().getMimeType("." + extension);
        //响应输出流
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(music, "utf-8"));
        response.setContentType(mimeType);
        response.getOutputStream().write(FileUtils.readFileToByteArray(file));
    }
}
