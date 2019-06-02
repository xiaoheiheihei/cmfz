package com.baizhi.dao;

import com.baizhi.entity.Guru;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GuruDao {
    //查询所有
    public List<Guru> queryAll(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    //添加
    public Integer add(Guru guru);

    //删除
    public Integer delete(String[] id);

    //修改
    public Integer update(Guru guru);

    //查询总条数
    public Integer count();

    //根据id查询
    public Guru findById(String id);

    public List<Guru> findAll();
}
