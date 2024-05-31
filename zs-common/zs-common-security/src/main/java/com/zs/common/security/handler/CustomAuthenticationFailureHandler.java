package com.zs.common.security.handler;


import com.alibaba.fastjson2.JSON;
import com.zs.common.aop.annotation.LoginLog;
import com.zs.common.core.core.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义登录失败处理
 *
 * @author 86740
 */

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @LoginLog(value = 2)
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {


        response.setContentType("application/json;charset=UTF-8");
        String s = JSON.toJSONString(new Result<>().error(500, "登录失败", exception.getMessage()));
        response.getWriter().println(s);


    }
}
