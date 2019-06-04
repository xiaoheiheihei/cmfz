package com.baizhi.shiro.realm;

import com.baizhi.dao.RoleDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Permission;
import com.baizhi.entity.Role;
import com.baizhi.service.AdminService;
import com.baizhi.util.GetBean;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyCmfzRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        AdminService adminService = GetBean.getBean(AdminService.class);
        Admin admin = adminService.findByUsername(primaryPrincipal);
        AuthorizationInfo authorizationInfo = null;
        if (admin != null) {
            authorizationInfo = new SimpleAuthorizationInfo();
            Admin admin1 = adminService.findById(admin.getId());
            System.out.println(admin1);
            List<Role> roles = admin1.getRoles();
            Set<String> sr = new HashSet<>();
            Set<String> sr1 = new HashSet<>();
            for (Role role : roles) {
                sr.add(role.getName());
                RoleDao roleDao = GetBean.getBean(RoleDao.class);
                List<Permission> permissions = roleDao.findById(role.getId());
                for (Permission permission : permissions) {
                    sr.add(permission.getName1());
                }
            }
            ((SimpleAuthorizationInfo) authorizationInfo).addRoles(sr);
            ((SimpleAuthorizationInfo) authorizationInfo).addStringPermissions(sr1);
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        AuthenticationInfo authenticationInfo = null;
        AdminService adminService = GetBean.getBean(AdminService.class);
        Admin admin = adminService.findByUsername(principal);
        if (admin != null) {
            authenticationInfo = new SimpleAuthenticationInfo(admin.getUsername(), admin.getPassword(), this.getName());
        }
        return authenticationInfo;
    }
}
