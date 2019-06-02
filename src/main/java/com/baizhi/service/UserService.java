package com.baizhi.service;

import com.baizhi.entity.User;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService {
    //查询所有
    public Map<String, Object> queryAll(Integer page, Integer rows);

    //添加
    public Map<String, Object> add(User user);

    //删除
    public Map<String, Object> delete(String[] id);

    //修改
    public Map<String, Object> update(User user);

    //查询总条数
    public Integer count();

    //根据id查询
    public User findById(String id);

    public Integer getCount(String create_date);

    void export(HttpSession session, HttpServletResponse response) throws Exception;

    public String[] getProvince();

    public Integer getProvince1(String province);
}
