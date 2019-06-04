package com.baizhi.test;

import com.baizhi.CmfzApplication;
import com.baizhi.dao.AdminDao;
import com.baizhi.dao.UserDao;
import com.baizhi.service.UserService;
import com.baizhi.shiro.realm.MyCmfzRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Test
    public void test() {
        Realm realm = new MyCmfzRealm();
        /* HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();*/
        /* credentialsMatcher.setHashAlgorithmName("Md5");*/
        /* credentialsMatcher.setHashIterations(1024);*/
        /* ((MyCmfzRealm) realm).setCredentialsMatcher(credentialsMatcher);*/
        SecurityManager securityManager = new DefaultWebSecurityManager(realm);
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken("zhangsan", "111111");
        try {
            subject.login(token);
            boolean aSuper = subject.hasRole("super");
            System.out.println(aSuper);
            boolean permitted = subject.isPermitted("user:add");
            System.out.println(permitted);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }
}
