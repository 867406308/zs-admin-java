package com.zs.common.security.provider;


import com.zs.common.security.service.CustomUserDetailsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * 用户名密码认证器
 *
 * @author 86740
 */
@Configuration
@Component
public class UserNameAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private CustomUserDetailsService customUserDetailsService;


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        // 1、去调用自己实现的UserDetailsService，返回UserDetails
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);


        // 2、密码进行检查，这里调用了PasswordEncoder，检查 UserDetails 是否可用。
        if (Objects.isNull(userDetails) || !passwordEncoder().matches(password, userDetails.getPassword())) {
            // 将用户名存储在HttpServletRequest的属性中
            request.setAttribute("username", username);

            throw new BadCredentialsException("账号或密码错误");
        }
        // 将用户名存储在HttpServletRequest的属性中
        request.setAttribute("username", username);
        // 3、返回经过认证的Authentication
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        result.setDetails(authentication.getDetails());
        // 将 Authentication 认证信息对象绑定到 SecurityContext即安全上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        String token = JwtUtil.createToken();
        return result;
    }


}