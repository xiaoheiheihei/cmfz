package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @RequestMapping("/queryAll")
    public Map<String, Object> queryAll(Integer page, Integer rows, String album_id) {
        Map<String, Object> map = chapterService.queryAll(page, rows, album_id);
        return map;
    }

    @RequestMapping("edit")
    public Map<String, Object> edit(String oper, Chapter chapter, String[] id, String album_id) {
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            map = chapterService.add(chapter, album_id);
        } else if ("edit".equals(oper)) {
            map = chapterService.update(chapter);
        } else if ("del".equals(oper)) {
            map = chapterService.delete(id);
        }
        return map;
    }

    @RequestMapping("upload")
    public String upload(MultipartFile audio_path, HttpSession session, String chapterId) {
        String originalFilename = audio_path.getOriginalFilename();
        System.out.println(originalFilename);
        Chapter chapter = null;
        if (originalFilename != null && !("").equals(originalFilename)) {
            //判断文件上传文件夹是否存在
            String realPath = session.getServletContext().getRealPath("/upload1/");
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdir();
            }
            String str = new Date().getTime() + "_" + originalFilename;
            try {
                audio_path.transferTo(new File(realPath, str));
                AudioFile read = AudioFileIO.read(new File(realPath, str));
                AudioHeader audioHeader = read.getAudioHeader();
                int trackLength = audioHeader.getTrackLength();
                String seconds = trackLength % 60 + "秒";
                String minute = trackLength / 60 + "分";
                long l = audio_path.getSize();
                String size = l / 1024 / 1024 + "MB";
                chapter = new Chapter();
                chapter.setId(chapterId);
                chapter.setDuration(minute + seconds);
                chapter.setSize(size);
                chapter.setAudio_path(str);
                chapter.setUpload_date(new Date());
                System.out.println(chapter);
                chapterService.update(chapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @RequestMapping("download")
    public void download(Chapter chapter, HttpSession session, HttpServletResponse response) {
        chapterService.downLoadAudio(chapter, response, session);
    }
}
