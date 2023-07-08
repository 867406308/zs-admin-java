package com.zs.framework.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * 自定义账号密码验证逻辑
 */
@Component
public interface CustomUserDetailsService {

    UserDetails loadUserByUsername(String username);
}
