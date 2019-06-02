package com.baizhi.controller;

import com.baizhi.entity.Time;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("queryAll")
    public Map<String, java.lang.Object> queryAll(Integer page, Integer rows) {
        Map<String, java.lang.Object> map = userService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("/edit")
    public Map<String, java.lang.Object> edit(String oper, User user, HttpSession session, String[] id) {
        Map<String, java.lang.Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            map = userService.add(user);
        } else if ("edit".equals(oper)) {
            if (("").equals(user.getHead_pic())) {
                user.setHead_pic(null);
            }
            map = userService.update(user);
        } else if ("del".equals(oper)) {
            map = userService.delete(id);
        }
        return map;
    }

    @RequestMapping("update")
    public Map<String, java.lang.Object> update(User user) {
        Map<String, java.lang.Object> map = userService.update(user);
        return map;
    }

    @RequestMapping("export")
    public void export(HttpSession session, HttpServletResponse response) throws Exception {
        userService.export(session, response);
    }

    @RequestMapping("getCount")
    public List<Map<String, List<java.lang.Object>>> getCount() {
        List<Map<String, List<java.lang.Object>>> mapList = new ArrayList<>();
        Map<String, List<java.lang.Object>> map = new HashMap<>();
        List<java.lang.Object> list = new ArrayList<>();
        List<java.lang.Object> list1 = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        for (int i = 0; i < 7; i++) {
            String create_date = sdf.format(new Date(d.getTime() - (long) i * 24 * 60 * 60 * 1000));
            Integer count = userService.getCount(create_date);
            list1.add(count);
            list.add(create_date);
        }
        map.put("value", list1);
        map.put("name", list);
        mapList.add(map);
        return mapList;
    }

    @RequestMapping("getP1")
    public List<Time> getP1() {
        List<Time> list = new ArrayList<>();
        String[] province = userService.getProvince();
        for (String s : province) {
            Integer count = userService.getProvince1(s);
            Time time = new Time();
            time.setName(s);
            time.setValue(count);
            list.add(time);
        }
        System.out.println(list);
        return list;
    }
}