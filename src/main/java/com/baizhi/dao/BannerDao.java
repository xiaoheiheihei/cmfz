package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    //查询所有轮播图信息
    public List<Banner> queryAll(@Param("page") Integer page, @Param("pageSize") Integer pageSize);

    //根据id查询轮播图信息
    public Banner findById(String id);

    //根据id修改轮播图信息
    public Integer update(Banner banner);

    //根据id批量删除
    public Integer delete(String[] id);

    //添加轮播图信息
    public Integer add(Banner banner);

    public Integer count();

}
