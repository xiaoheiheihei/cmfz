package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/album")
@RestController
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @RequestMapping("queryAll")
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String, Object> map = albumService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("upload")
    public @ResponseBody
    String upload(MultipartFile cover_pic, HttpSession session, String albumId) {
        String originalFilename = cover_pic.getOriginalFilename();
        Album album = null;
        if (originalFilename != null && !("").equals(originalFilename)) {
            //判断文件上传文件夹是否存在
            String realPath = session.getServletContext().getRealPath("/upload/");
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdir();
            }
            String str = new Date().getTime() + "_" + originalFilename;
            try {
                cover_pic.transferTo(new File(realPath, str));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (albumId != null) {
                album = albumService.findId(albumId);
                album.setCover_pic(str);
            }
        }
        albumService.update(album);
        return null;
    }

    @RequestMapping("/edit")
    public @ResponseBody
    Map<String, Object> edit(String oper, Album album, HttpSession session, String[] id) {
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            map = albumService.add(album);
        } else if ("edit".equals(oper)) {
            if ("".equals(album.getCover_pic())) {
                album.setCover_pic(null);
            }
            map = albumService.update(album);
        } else if ("del".equals(oper)) {
            map = albumService.delete(id);
        }
        return map;
    }


}
