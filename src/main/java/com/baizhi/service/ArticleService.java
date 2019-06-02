package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {
    //查询所有
    public Map<String, Object> queryAll(Integer page, Integer rows);

    //添加
    public Map<String, Object> add(Article article);

    //删除
    public Map<String, Object> delete(String[] id);

    //修改
    public Map<String, Object> update(Article article);

    //查询总条数
    public Integer count();

    //根据id查询
    public Article findById(String id);
}
