package com.baizhi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/kindeditor")
public class KindeditorController {

    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile img, HttpSession session, HttpServletRequest request) throws IOException {
        String realPath = session.getServletContext().getRealPath("/img/");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = img.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + filename;
        img.transferTo(new File(realPath, newFileName));

        Map<String, Object> map = new HashMap<>();
        map.put("error", 0);

        String scheme = request.getScheme();//http
        InetAddress localHost = Inet4Address.getLocalHost();//本机ip
        String address = localHost.getHostAddress();
        int port = request.getServerPort();//获取端口号
        String path = request.getContextPath();//项目名

        String url = scheme + "://" + address + ":" + port + path + "/img/" + newFileName;
        map.put("url", url);
        return map;
    }

    @RequestMapping("getAllImg")
    public Map<String, Object> getAllImg(HttpServletRequest request) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        String realPath = request.getSession().getServletContext().getRealPath("/img/");
        File file = new File(realPath);
        String[] names = file.list();
        for (String name : names) {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("is_dir", false);
            hashMap.put("has_file", false);
            File file1 = new File(realPath, name);
            long length = file1.length();
            hashMap.put("filesize", length);
            hashMap.put("is_photo", true);
            String s = name.substring(name.lastIndexOf(".") + 1);
            hashMap.put("filetype", s);
            hashMap.put("filename", name);
            boolean b = name.contains("_");
            if (b) {
                String s1 = name.split("_")[0];
                Long aLong = Long.valueOf(s1);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String s2 = format.format(aLong);
                hashMap.put("datetime", s2);
            }
            if (!b) {
                hashMap.put("datetime", new Date());
            }
            hashMap.put("datetime", name);
            hashMap.put("dir_path", "");
            list.add(hashMap);
        }
        String scheme = request.getScheme();//http
        InetAddress localHost = Inet4Address.getLocalHost();//本机ip
        String address = localHost.getHostAddress();
        int port = request.getServerPort();//获取端口号
        String path = request.getContextPath();//项目名

        String url = scheme + "://" + address + ":" + port + path + "/img/";

        map.put("moveup_dir_path", "");
        map.put("current_dir_path", "");
        map.put("current_url", url);
        map.put("total_count", names.length);
        map.put("file_list", list);

        return map;
    }

}
