package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ChapterDao chapterDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Map<String, Object> queryAll(Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        Integer records = albumDao.count();
        Integer count = (records % pageSize == 0) ? (records / pageSize) : (records / pageSize + 1);
        Integer start = (page - 1) * pageSize;
        List<Album> albums = albumDao.queryAll(start, pageSize);
        map.put("rows", albums);
        map.put("page", page);
        map.put("total", count);
        map.put("records", records);
        return map;
    }

    @Override
    public Map<String, Object> add(Album album) {
        Map<String, Object> map = new HashMap<>();
        album.setId(UUID.randomUUID().toString().replace("-", ""));
        album.setUpload_date(new Date());
        album.setCount(5);
        Integer add = albumDao.add(album);
        if (add == 1) {
            map.put("message", "添加成功！");
        } else {
            map.put("message", "添加失败！");
        }
        map.put("albumId", album.getId());
        return map;
    }

    @Override
    public Map<String, Object> delete(String[] id) {
        Map<String, Object> map = new HashMap<>();
        Integer delete = albumDao.delete(id);
        if (delete == 1) {
            map.put("message", "删除成功！");
        } else {
            map.put("message", "删除失败！");
        }
        return map;
    }

    @Override
    public Map<String, Object> update(Album album) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(album);
        Integer update = albumDao.update(album);
        if (update == 1) {
            map.put("message", "更新成功！");
        } else {
            map.put("message", "更新失败！");
        }
        map.put("albumId", album.getId());
        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Integer count() {
        Integer count = albumDao.count();
        return count;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Album findId(String id) {
        Album album = albumDao.findById(id);
        return album;
    }
}
