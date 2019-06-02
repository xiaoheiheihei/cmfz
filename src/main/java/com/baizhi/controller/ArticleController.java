package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping("queryAll")
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String, Object> map = articleService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map<String, Object> edit(Article article) {
        System.out.println(article);
        Map<String, Object> map = articleService.add(article);
        return map;
    }

    @RequestMapping("update")
    public Map<String, Object> update(Article article) {
        System.out.println(article);
        Map<String, Object> map = articleService.update(article);
        return map;
    }

    @RequestMapping("delete")
    public Map<String, Object> delete(String[] id) {
        System.out.println(id);
        Map<String, Object> map = articleService.delete(id);
        return map;
    }
}
