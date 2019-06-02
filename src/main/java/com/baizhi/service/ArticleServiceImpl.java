package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Integer records = articleDao.count();
        Integer count = (records % rows == 0) ? (records / rows) : (records / rows + 1);
        Integer start = (page - 1) * rows;
        List<Article> articles = articleDao.queryAll(start, rows);
        map.put("rows", articles);
        map.put("page", page);
        map.put("total", count);
        map.put("records", records);
        return map;
    }

    @Override
    public Map<String, Object> add(Article article) {
        Map<String, Object> map = new HashMap<>();
        article.setId(UUID.randomUUID().toString().replace("_", ""));
        article.setCreate_date(new Date());
        article.setUpload_date(new Date());
        Integer add = articleDao.add(article);
        if (add == 1) {
            map.put("message", "添加成功！");
        } else {
            map.put("message", "添加失败！");
        }
        return map;
    }

    @Override
    public Map<String, Object> delete(String[] id) {
        Map<String, Object> map = new HashMap<>();
        Integer delete = articleDao.delete(id);
        if (delete != 0) {
            map.put("message", "删除成功！");
        } else {
            map.put("message", "删除失败！");
        }
        return map;
    }

    @Override
    public Map<String, Object> update(Article article) {
        Map<String, Object> map = new HashMap<>();
        Integer update = articleDao.update(article);
        if (update != 0) {
            map.put("message", "更新成功！");
        } else {
            map.put("message", "更新失败！");
        }
        return map;
    }

    @Override
    public Integer count() {

        return null;
    }

    @Override
    public Article findById(String id) {
        Article article = articleDao.findById(id);
        return article;
    }
}
