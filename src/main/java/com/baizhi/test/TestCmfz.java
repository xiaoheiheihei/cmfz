package com.baizhi.test;

import com.baizhi.CmfzApplication;
import com.baizhi.dao.AdminDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzApplication.class)
public class TestCmfz {
    @Autowired
    AdminDao adminDao;
    @Autowired
    UserDao userService;
    @Autowired
    UserService u;

    @Test
    public void test1() {
        List<User> users = userService.queryAll(0, 5);
        Map<String, Object> map = u.queryAll(1, 5);
    }

    @Test
    public void test2() throws ParseException {
        /*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date dt=null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.DATE,-1);*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        System.out.println(sdf.format(new Date(d.getTime() - (long) 24 * 60 * 60 * 1000)));


    }
}
