package com.baizhi.shiro.realm;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class shiroFilter {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(MyCmfzRealm myCmfzRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(myCmfzRealm);
        return securityManager;
    }

    @Bean
    public MyCmfzRealm myCmfzRealm() {
        MyCmfzRealm myCmfzRealm = new MyCmfzRealm();
        return myCmfzRealm;
    }


}
