package com.baizhi.service;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.DelCache;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    @AddCache
    public Map<String, Object> queryAll(Integer page, Integer pageSize, String album_id) {
        Map<String, Object> map = new HashMap<>();
        Integer records = chapterDao.count(album_id);
        Integer count = (records % pageSize == 0) ? (records / pageSize) : (records / pageSize + 1);
        Integer start = (page - 1) * pageSize;
        List<Chapter> chapters = chapterDao.queryAll(start, pageSize, album_id);
        map.put("rows", chapters);
        map.put("page", page);
        map.put("total", count);
        map.put("records", records);
        return map;
    }

    @Override
    @DelCache
    public Map<String, Object> add(Chapter chapter, String album_id) {
        Map<String, Object> map = new HashMap<>();
        chapter.setAlbum_id(album_id);
        chapter.setId(UUID.randomUUID().toString().replace("-", ""));
        Integer add = chapterDao.add(chapter);
        if (add == 1) {
            map.put("message", "添加成功！");
        } else {
            map.put("message", "添加失败！");
        }
        map.put("chapterId", chapter.getId());
        return map;
    }

    @Override
    @DelCache
    public Map<String, Object> delete(String[] id) {
        Map<String, Object> map = new HashMap<>();
        Integer delete = chapterDao.delete(id);

        if (delete != 0) {
            map.put("message", "删除成功！");
        } else {
            map.put("message", "删除失败！");
        }
        return map;
    }

    @Override
    @DelCache
    public Map<String, Object> update(Chapter chapter) {
        Map<String, Object> map = new HashMap<>();
        Integer update = chapterDao.update(chapter);
        if (update == 1) {
            map.put("message", "更新成功！");
        } else {
            map.put("message", "更新失败！");
        }
        map.put("chapterId", chapter.getId());
        return map;
    }

    @Override
    @AddCache
    public Integer count(String album_id) {
        Integer count = chapterDao.count(album_id);
        return count;
    }

    @Override
    @AddCache
    public Chapter findById(String id) {
        Chapter chapter = chapterDao.findById(id);
        return chapter;
    }

    @Override
    public void downLoadAudio(Chapter chapter, HttpServletResponse response, HttpSession session) {
        //获得音频的目录
        String realPath = session.getServletContext().getRealPath("/upload1");
        //获得音频的文件
        File file = new File(realPath, chapter.getAudio_path());
        String[] s = chapter.getAudio_path().split("_");
        String ss = s[1];
        try {
            String encode = URLEncoder.encode(ss, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //3.设置响应头(以附件的形式下载)
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            FileUtils.copyFile(file, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
