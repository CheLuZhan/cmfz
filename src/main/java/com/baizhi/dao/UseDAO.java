package com.baizhi.dao;

import com.baizhi.entity.Province;
import com.baizhi.entity.Use;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UseDAO {
    //查询用户活跃度
    @Select("select count(id) from `use` where datediff(now(),date)<#{day}")
    public Integer selectVitality(Integer day);

    //查询用户的省份
    @Select("select province name,count(id) value from `use` where sex=#{sex} GROUP BY province")
    public List<Province> selectByProvince(String sex);

    //添加用户
    @Insert("insert into `use` values(#{id},#{name},#{date},#{province},#{sex})")
    public void add(Use use);
}
