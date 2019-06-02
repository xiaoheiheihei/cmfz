package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {
    //查询所有
    public List<Chapter> queryAll(@Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("album_id") String album_id);

    //查询所有
    public Integer count(String album_id);

    //添加
    public Integer add(Chapter chapter);

    //删除
    public Integer delete(String[] id);

    //修改
    public Integer update(Chapter chapter);

    //根据id查询
    public Chapter findById(String id);
}
