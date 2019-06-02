package com.baizhi.service;

import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Map<String, Object> queryAll(Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        Integer count = guruDao.count();
        Integer start = (page - 1) * pageSize;
        List<Guru> gurus = guruDao.queryAll(start, pageSize);
        Integer records = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        map.put("page", page);
        map.put("records", records);
        map.put("total", count);
        map.put("rows", gurus);
        return map;
    }

    @Override
    public Map<String, Object> add(Guru guru) {
        return null;
    }

    @Override
    public Map<String, Object> delete(String[] id) {
        return null;
    }

    @Override
    public Map<String, Object> update(Guru guru) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public Guru findById(String id) {
        return null;
    }

    @Override
    public List<Guru> findAll() {
        return null;
    }
}
