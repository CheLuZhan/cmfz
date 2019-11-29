package com.baizhi.service;

import com.baizhi.dao.UseDAO;
import com.baizhi.entity.Province;
import com.baizhi.entity.Use;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UseServiceImpl implements UseService {
    @Autowired
    UseDAO useDAO;

    @Override
    public List<Integer> selectState() {
        ArrayList<Integer> list = new ArrayList<>();
        Integer integer1 = useDAO.selectVitality(7);
        Integer integer2 = useDAO.selectVitality(14);
        Integer integer3 = useDAO.selectVitality(21);
        list.add(integer1);
        list.add(integer2);
        list.add(integer3);
        return list;
    }

    @Override
    public Map<String, List<Province>> selectByProvince() {
        List<Province> list1 = useDAO.selectByProvince("M");
        List<Province> list2 = useDAO.selectByProvince("W");
        Map<String, List<Province>> map = new HashMap<>();
        map.put("M", list1);
        map.put("W", list2);
        return map;
    }

    @Override
    public void add(Use use) {
        String s = UUID.randomUUID().toString();
        use.setId(s);
        use.setName("zxc");
        use.setDate(new Date());
        use.setProvince("河南");
        use.setSex("M");

        GoEasy goEasy = null;
        try {
            useDAO.add(use);
            goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-0a8a85328dd5485aa6fa93b71ce91d22");
            goEasy.publish("my_channel", "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            goEasy.publish("my_channel", "添加失败");

        }
    }

}
