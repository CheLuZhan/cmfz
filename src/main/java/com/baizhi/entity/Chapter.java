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
public class Chapter implements Serializable {  //章节
    private String id;
    private String title;
    private String size;
    private String time;    //时长
    private Date uploadTime; //上传时间
    private String music;    //音频名
    private String albumId; // 专辑id
}
