package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    //查询所有
    public Map<String, Object> queryAll(Integer page, Integer pageSize);

    //添加
    public Map<String, Object> add(Album album);

    //删除
    public Map<String, Object> delete(String[] id);

    //修改
    public Map<String, Object> update(Album album);

    //查询总条数
    public Integer count();

    //根据id查询
    public Album findId(String id);

}
