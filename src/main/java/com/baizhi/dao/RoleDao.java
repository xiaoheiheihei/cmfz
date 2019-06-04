package com.baizhi.dao;

import com.baizhi.entity.Permission;

import java.util.List;

public interface RoleDao {
    //根据id查询
    public List<Permission> findById(String id);
}
