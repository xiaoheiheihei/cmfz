package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    //查询所有
    public List<Article> queryAll(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    //添加
    public Integer add(Article article);

    //删除
    public Integer delete(String[] id);

    //修改
    public Integer update(Article article);

    //查询总条数
    public Integer count();

    //根据id查询
    public Article findById(String id);
}
