package com.baizhi.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("kind")
@RestController
public class KindeditorController {
    @RequestMapping("edit")
    public Map<String, Object> uploadImg(HttpServletRequest request, MultipartFile img) {
        Map<String, Object> map = new HashMap<>();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String originalFilename = img.getOriginalFilename();
        String s = new Date().getTime() + "_" + originalFilename;
        try {
            img.transferTo(new File(realPath, s));
            map.put("error", 0);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error", 1);
        }
        try {
            String scheme = request.getScheme();//http
            InetAddress localHost = InetAddress.getLocalHost();
            String localhost = localHost.toString().split("/")[1];
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String url = scheme + "://" + localhost + ":" + serverPort + contextPath + "/upload/" + s;
            map.put("url", url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;


    }

    @RequestMapping("getAll")
    public Map<String, Object> getAllImg(MultipartFile img, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/");
        File file = new File(realPath);
        String[] imgs = file.list();
        for (String img1 : imgs) {
            Map<String, Object> ma = new HashMap<>();
            File file1 = new File(realPath, img1);
            long length = file1.length();
            String filetype = FilenameUtils.getExtension(img1);
            String date = img1.split("_")[0];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Long ll = new Long(date);
            Date date1 = new Date(ll);
            String format = sdf.format(date1);
            ma.put("is_dir", false);
            ma.put("has_file", false);
            ma.put("filesize", length);
            ma.put("dir_path", "");
            ma.put("is_photo", true);
            ma.put("filetype", filetype);
            ma.put("filename", img1);
            ma.put("datetime", format);
            list.add(ma);
        }
        try {
            String scheme = request.getScheme();//http
            InetAddress localHost = InetAddress.getLocalHost();
            String localhost = localHost.toString().split("/")[1];
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String url = scheme + "://" + localhost + ":" + serverPort + contextPath + "/upload/";
            map.put("moveup_dir_path", "");
            map.put("current_fir_path", "");
            map.put("current_url", url);
            int size = list.size();
            map.put("total", size);
            map.put("file_list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(map);
        return map;
    }

    @RequestMapping("add")
    public void add(String content) {
        System.out.println(content);
    }
}
