package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    //查询所有
    public List<Album> queryAll(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    //添加
    public Integer add(Album album);

    //删除
    public Integer delete(String[] id);

    //修改
    public Integer update(Album album);

    //查询总条数
    public Integer count();

    //根据id查询
    public Album findById(String id);
}
