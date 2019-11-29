package com.baizhi.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
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
public class Banner implements Serializable {
    @Excel(name = "编号")
    private String id;
    @Excel(name = "标题")
    private String title;
    @Excel(name = "描述")
    private String description;
    @Excel(name = "日期", format = "yyyy-MM-dd")
    private Date date;
    @Excel(name = "状态", replace = {"展示_T", "不展示_N"})
    private String state;
    @Excel(name = "图片路径", type = 2)
    private String src;
}
