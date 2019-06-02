package com.baizhi.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Integer records = userDao.count();
        Integer count = records % rows == 0 ? records / rows : records / rows + 1;
        Integer start = (page - 1) * rows;
        List<User> users = userDao.queryAll(start, rows);
        map.put("page", page);
        map.put("records", records);
        map.put("total", count);
        map.put("rows", users);
        return map;
    }

    @Override
    public Map<String, Object> add(User user) {
        Map<String, Object> map = new HashMap<>();

        return null;
    }

    @Override
    public Map<String, Object> delete(String[] id) {
        return null;
    }

    @Override
    public Map<String, Object> update(User user) {
        User user1 = userDao.findById(user.getId());
        System.out.println(user1);
        Map<String, Object> map = new HashMap<>();
        if ("冻结".equals(user1.getStatus())) {
            user1.setStatus("激活");
        } else {
            user1.setStatus("冻结");
        }
        Integer update = userDao.update(user1);
        if (update == 1) {
            map.put("message", "更新成功！");
        } else {
            map.put("message", "更新失败！");
        }
        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Integer count() {
        Integer count = userDao.count();
        return count;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public Integer getCount(String create_date) {
        Integer count = userDao.getCount(create_date);
        return count;
    }

    @Override
    public void export(HttpSession session, HttpServletResponse response) throws Exception {
        ServletOutputStream outputStream = null;
        List<User> list = userDao.findAll();
        String realPath = session.getServletContext().getRealPath("/upload/");
        for (User user : list) {
            String head_pic = user.getHead_pic();
            user.setHead_pic(realPath + head_pic);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("xxx省市用户信息表", "用户详情"), User.class, list);
        try {
            String encode = URLEncoder.encode("user.xls", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + encode);
            outputStream = response.getOutputStream();
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
        }


    }

    @Override
    public String[] getProvince() {
        String[] province = userDao.getProvince();
        return province;
    }

    @Override
    public Integer getProvince1(String province) {
        Integer count = userDao.getProvince1(province);
        return count;
    }
}
