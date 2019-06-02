package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.Map;

public interface BannerService {
    //查询所有轮播图信息
    public Map<String, Object> queryAll(Integer page, Integer rows);

    //根据id查询轮播图信息
    public Banner findById(String id);

    //根据id修改轮播图信息
    public Map<String, Object> update(Banner banner);

    //根据id批量删除
    public Map<String, Object> delete(String[] id);

    //添加轮播图信息
    public Map<String, Object> add(Banner banner);
}
