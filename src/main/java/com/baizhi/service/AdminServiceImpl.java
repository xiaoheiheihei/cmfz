package com.baizhi.service;

import com.baizhi.annotation.AddCache;
import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    @AddCache
    public Map<String, String> findByUser(Admin admin, String enCode, HttpSession session) {
        Map<String, String> map = new HashMap<>();
        Admin admin1 = adminDao.findByUser(admin.getUsername());
        String message = null;
        if (admin1 == null) {
            message = "用户名输入错误！";
            /* throw new RuntimeException("用户名输入错误！");*/
        } else {
            if (!admin1.getPassword().equals(admin.getPassword())) {
                message = "密码输入错误！";
                /* throw new RuntimeException("密码输入错误！");*/
            } else {
                if (!session.getAttribute("code").equals(enCode)) {
                    message = "验证码输入错误！";
                } else {
                    session.setAttribute("admin", admin1);
                }
            }
        }
        map.put("message", message);
        return map;
    }

}
