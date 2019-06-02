package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @RequestMapping("/queryAll")
    public @ResponseBody
    Map<String, Object> queryAll(Integer page, Integer rows) {

        Map<String, Object> map = bannerService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("upload")
    public @ResponseBody
    String upload(MultipartFile img_pic, HttpSession session, String bannerId) {
        String originalFilename = img_pic.getOriginalFilename();
        Banner banner = null;
        if (originalFilename != null && !("").equals(originalFilename)) {
            //判断文件上传文件夹是否存在
            String realPath = session.getServletContext().getRealPath("/upload/");
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdir();
            }
            String str = new Date().getTime() + "_" + originalFilename;
            try {
                img_pic.transferTo(new File(realPath, str));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bannerId != null) {
                banner = bannerService.findById(bannerId);
                banner.setImg_pic(str);
            }
        }
        bannerService.update(banner);
        return null;
    }

    @RequestMapping("/edit")
    public @ResponseBody
    Map<String, Object> edit(String oper, Banner banner, HttpSession session, String[] id) {
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            map = bannerService.add(banner);
        } else if ("edit".equals(oper)) {
            if ("".equals(banner.getImg_pic())) {
                banner.setImg_pic(null);
            }
            map = bannerService.update(banner);
        } else if ("del".equals(oper)) {
            map = bannerService.delete(id);
        }
        return map;
    }

}
