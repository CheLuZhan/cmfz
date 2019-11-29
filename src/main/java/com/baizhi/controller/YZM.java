package com.baizhi.controller;

import com.baizhi.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/YZM")
public class YZM {
    @Autowired
    ImageCodeUtil imageCodeUtil;

    //生成验证码
    @RequestMapping("CodeUtil")
    public String CodeUtil(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        response.setHeader("cache", "no-cache");
        //获取Session
        HttpSession session = request.getSession();
        OutputStream outputStream = response.getOutputStream();
        String securityCode = imageCodeUtil.getSecurityCode();

        session.setAttribute("code", securityCode);
        // 取得验证码图片并输出
        BufferedImage image = imageCodeUtil.createImage(securityCode);

        ImageIO.write(image, "png", outputStream);

        outputStream.flush();
        outputStream.close();
        return null;
    }
}
