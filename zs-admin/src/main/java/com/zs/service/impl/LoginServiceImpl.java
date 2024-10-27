package com.zs.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.zs.common.core.constant.RedisConstants;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.model.LoginUserInfo;
import com.zs.common.core.model.SysUser;
import com.zs.common.core.utils.CryptoUtil;
import com.zs.common.core.utils.IpUtils;
import com.zs.common.redis.config.RedisUtil;
import com.zs.common.security.handler.CustomAuthenticationFailureHandler;
import com.zs.common.security.handler.CustomAuthenticationSuccessHandler;
import com.zs.common.security.model.TokenVo;
import com.zs.common.security.utils.JwtUtil;
import com.zs.domain.params.LoginParams;
import com.zs.service.ILoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zs
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Resource
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    public void login(LoginParams loginParams, HttpServletRequest request, HttpServletResponse response) {

        if (!StringUtils.hasText(loginParams.getUsername())) {
            throw new ZsException("用户名不能为空!");
        }
        if (!StringUtils.hasText(loginParams.getPassword())) {
            throw new ZsException("密码不能为空!");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginParams.getUsername(), loginParams.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(token);
            // 认证成功处理
            processAuthenticationSuccess(authentication, request, response);

        } catch (AuthenticationException e) {
            // 认证失败处理
            processAuthenticationFailure(request, response, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processAuthenticationSuccess(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        customAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
    }

    private void processAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        try {
            customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }






}
