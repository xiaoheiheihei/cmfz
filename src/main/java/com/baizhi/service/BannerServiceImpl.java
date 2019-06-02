package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDao bannerDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Map<String, Object> queryAll(Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        Integer pageCount = 0;
        Integer count = bannerDao.count();
        Map<String, Object> map = new HashMap<>();
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        List<Banner> banners = bannerDao.queryAll(page, pageSize);
        map.put("page", page);
        map.put("total", pageCount);
        map.put("records", count);
        map.put("rows", banners);
        return map;
    }

    @Override
    public Banner findById(String id) {

        Banner banner = bannerDao.findById(id);
        return banner;
    }

    @Override
    public Map<String, Object> update(Banner banner) {
        Map<String, Object> map = new HashMap<>();
        Integer update = bannerDao.update(banner);
        if (update == 1) {
            map.put("message", "更新成功！");
        } else {
            map.put("message", "更新失败！");
        }
        map.put("bannerId", banner.getId());
        return map;
    }

    @Override
    public Map<String, Object> delete(String[] id) {
        Map<String, Object> map = new HashMap<>();
        Integer delete = bannerDao.delete(id);
        if (delete == 1) {
            map.put("message", "删除成功！");
        } else {
            map.put("message", "删除失败！");
        }
        return map;
    }

    @Override
    public Map<String, Object> add(Banner banner) {
        Map<String, Object> map = new HashMap<>();
        banner.setId(UUID.randomUUID().toString().replace("-", ""));
        banner.setCreate_date(new Date());
        Integer add = bannerDao.add(banner);
        if (add == 1) {
            map.put("message", "添加成功！");
        } else {
            map.put("message", "添加失败！");
        }
        map.put("bannerId", banner.getId());
        return map;
    }
}
