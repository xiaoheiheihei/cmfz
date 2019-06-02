package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //查询所有
    public List<User> queryAll(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    //添加
    public Integer add(User user);

    //删除
    public Integer delete(String[] id);

    //修改
    public Integer update(User user);

    //查询总条数
    public Integer count();

    //根据id查询
    public User findById(String id);

    public List<User> findAll();

    public Integer getCount(String create_date);

    public String[] getProvince();

    public Integer getProvince1(String province);
}
