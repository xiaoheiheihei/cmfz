package com.baizhi.controller;


import com.baizhi.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/guru")
public class GuruController {
    @Autowired
    GuruService guruService;

    @RequestMapping("queryAll")
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String, Object> map = guruService.queryAll(page, rows);
        System.out.println(map);
        return map;
    }
}
