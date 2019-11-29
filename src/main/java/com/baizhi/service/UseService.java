package com.baizhi.service;

import com.baizhi.entity.Province;
import com.baizhi.entity.Use;

import java.util.List;
import java.util.Map;

public interface UseService {
    //查询状态
    public List<Integer> selectState();

    //查询用户的省份
    public Map<String, List<Province>> selectByProvince();

    //添加用户
    public void add(Use use);
}
