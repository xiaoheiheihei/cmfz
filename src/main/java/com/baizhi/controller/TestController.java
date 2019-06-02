package com.baizhi.controller;

import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.BannerService;
import com.baizhi.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    AlbumService albumService;
    @Autowired
    ArticleService articleService;
    @Autowired
    BannerService bannerService;
    @Autowired
    GuruService guruService;

    @RequestMapping("/first_page")
    public Map<String, Object> first_page(String all, String wen, String si,
                                          String ssyj, String xmfy) {
        Map<String, Object> map = new HashMap<>();
        if (all == null || wen == null || si == null) {
            map.put("error", "信息不全！");
        } else {
            if (all != null) {
                Map<String, Object> map1 = bannerService.queryAll(1, 5);
                map.put("banner", map1);
                Map<String, Object> map2 = albumService.queryAll(1, 5);
                map.put("album", map2);
                Map<String, Object> map3 = articleService.queryAll(1, 5);
                map.put("article", map3);
            } else if (wen != null) {
                Map<String, Object> map2 = albumService.queryAll(1, 5);
                map.put("album", map2);
            } else if (si != null) {
                if (ssyj != null) {
                    Map<String, Object> map2 = guruService.queryAll(1, 5);
                    map.put("album", map2);
                } else if (xmfy != null) {
                    Map<String, Object> map2 = guruService.queryAll(1, 5);
                    map.put("album", map2);
                }
            }
        }
        return map;
    }
}
