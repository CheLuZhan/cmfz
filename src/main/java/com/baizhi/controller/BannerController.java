package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    //查询所有
    @RequestMapping("getAllBanner")
    public Map<String, Object> getAllBanner(Integer page, Integer rows) {
        Map<String, Object> map = bannerService.selectAllBanner(page, rows);
        return map;
    }

    //编辑轮播图
    @RequestMapping("edit")
    public String edit(String oper, Banner banner, String[] id) {
        if (oper.equals("add")) {    //add
            bannerService.add(banner);
            return banner.getId();
        } else if (oper.equals("edit")) {   //update
            bannerService.update(banner);
        } else if (oper.equals("del")) {
            bannerService.delete(id);
        }
        return null;
    }

    //上传
    @RequestMapping("upload")
    public void upload(MultipartFile src, String bid, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/img/");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = src.getOriginalFilename();//获取文件名
        String newFileName = new Date().getTime() + "_" + filename;
        try {
            src.transferTo(new File(file, newFileName));//拷貝文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        //修改数据库的路径
        bannerService.updateSrc(newFileName, bid);
    }

    //下载文档
    @RequestMapping("down")
    public void down(HttpSession session, HttpServletResponse response) throws Exception {
        //获取轮播图的绝对路径
        String imgPath = session.getServletContext().getRealPath("/img/");
        //查询所有轮播图 修改图片绝对路径
        List<Banner> list = bannerService.selectAll();
        for (Banner banner : list) {
            banner.setSrc(imgPath + banner.getSrc());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("所有轮播图", "轮播图"),
                Banner.class, list);
        //获取绝对路径
        String realPath = session.getServletContext().getRealPath("/banner/");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //导出文件到项目 目录
        workbook.write(new FileOutputStream(new File(file, "banner.xls")));

        //下载文档
        File file1 = new File(realPath, "banner.xls");
        //获取文件响应类型
        String extension = FilenameUtils.getExtension("banner.xls");
        String mimeType = session.getServletContext().getMimeType("." + extension);

        //响应输出流
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode("banner.xls", "utf-8"));
        response.setContentType(mimeType);
        response.getOutputStream().write(FileUtils.readFileToByteArray(file1));
    }


}
