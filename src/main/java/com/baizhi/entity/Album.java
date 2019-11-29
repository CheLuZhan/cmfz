package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Album implements Serializable {   //专辑
    private String id;
    private String title;
    private Double grade;   //分数
    private String author;  //作者
    private String announcer;//播音员
    private Integer number; //章节数
    private String intro;   //简介
    private String state;
    private Date publishTime; //发行时间
    private Date uploadTime;  //上传时间
    private String src;     //图片
}
