/*
package com.baizhi.conf;

import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduleTask {
    @Autowired
    ArticleService articleService;


    //3.添加定时任务

    @Scheduled(fixedRate=10000)
    private void configureTasks() {
        System.out.println("time==============>"+new Date());
        articleService.add();
    }
}*/
