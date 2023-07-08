package com.zs.framework.security.handler;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson2.JSON;
import com.zs.common.annotation.LoginLog;
import com.zs.common.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 自定义登录失败处理
 */

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @LoginLog(value = 2)
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        response.setContentType("application/json;charset=UTF-8");
        String s = JSON.toJSONString(new Result().error(500, "登录失败", exception.getMessage()));
        response.getWriter().println(s);

    }
}
