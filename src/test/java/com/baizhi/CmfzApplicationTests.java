package com.baizhi;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.dao.*;
import com.baizhi.entity.*;
import com.baizhi.esrepository.ArticleRepository;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class CmfzApplicationTests {
    @Autowired
    BannerDAO bannerDAO;
    @Autowired
    AlbumDAO albumDAO;
    @Autowired
    ChapterDAO chapterDAO;
    @Autowired
    TransportClient transportClient;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticleDAO articleDAO;
    @Autowired
    UseDAO useDAO;


    @Test
    public void selectCount() {
        Integer count = bannerDAO.selectCount();
        System.out.println("count = " + count);
    }

    @Test
    public void selectAllBanner() {
        List<Banner> banners = bannerDAO.selectAllBanner(0, 2);
        for (Banner banner : banners) {
            System.out.println("banner = " + banner);
        }
    }

    @Test
    public void getAlbum() {
        List<Album> albums = albumDAO.selectAllAlbum(0, 2);
        for (Album album : albums) {
            System.out.println("album = " + album);
        }
    }

    @Test
    public void add() {
        Integer count = chapterDAO.selectCount("df3ae5b1-8582-4d51-8ac5-f5337f5716b4");


        Chapter chapter = new Chapter();
        chapter.setId("123");
        chapter.setAlbumId("df3ae5b1-8582-4d51-8ac5-f5337f5716b4");
        chapterDAO.add(chapter);
        Integer count2 = chapterDAO.selectCount("df3ae5b1-8582-4d51-8ac5-f5337f5716b4");
        System.out.println("count = " + count);
        System.out.println("count2 = " + count2);

    }

    @Test
    public void addTest() throws IOException {
        List<Article> all = articleDAO.getAll();
        for (Article article : all) {
            articleRepository.add(article);
        }
    }

    @Test
    public void selectVitality() {
        Integer integer = useDAO.selectVitality(7);
        System.out.println("integer = " + integer);
    }

    @Test
    public void testInsert() {
        Use use = new Use();
        use.setId(UUID.randomUUID().toString());
        use.setSex("1");
        use.setProvince("河南");
        use.setName("xxx");
        use.setDate(new Date());
        useDAO.add(use);
    }

    //时间排序轮播图
    @Test
    public void selectByTime() {
        List<Banner> list = bannerDAO.selectByTime();
        for (Banner banner : list) {
            System.out.println(banner);
        }
    }

    @Test
    public void testSms() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FrufudC4vu8wjwZdSGA", "F9wnzYOLvd1rrVdF5DeZXYsl0e2UN0");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "15038702090");
        request.putQueryParameter("SignName", "橙子不挑食");
        request.putQueryParameter("TemplateCode", "SMS_178766680");
        request.putQueryParameter("TemplateParam", "{\"code\":\"520\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
