package com.baizhi.service;

import com.baizhi.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    //查询所有
    public Map<String, Object> queryAll(Integer page, Integer pageSize);

    //添加
    public Map<String, Object> add(Guru guru);

    //删除
    public Map<String, Object> delete(String[] id);

    //修改
    public Map<String, Object> update(Guru guru);

    //查询总条数
    public Integer count();

    //根据id查询
    public Guru findById(String id);

    public List<Guru> findAll();
}
