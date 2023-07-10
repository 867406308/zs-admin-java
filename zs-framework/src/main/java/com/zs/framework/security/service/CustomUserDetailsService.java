package com.zs.framework.security.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


/**
 * 自定义账号密码验证逻辑
 */
@Component
public interface CustomUserDetailsService {

    UserDetails loadUserByUsername(String username);
}
