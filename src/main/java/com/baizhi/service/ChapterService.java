package com.baizhi.service;

import com.baizhi.entity.Chapter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface ChapterService {
    //查询所有
    public Map<String, Object> queryAll(Integer page, Integer pageSize, String album_id);

    //添加
    public Map<String, Object> add(Chapter chapter, String album_id);

    //删除
    public Map<String, Object> delete(String[] id);

    //修改
    public Map<String, Object> update(Chapter chapter);

    //查询总条数
    public Integer count(String album_id);

    //根据id查询
    public Chapter findById(String id);

    void downLoadAudio(Chapter chapter, HttpServletResponse response, HttpSession session);
}
