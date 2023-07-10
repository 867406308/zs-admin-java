package com.zs.framework.security.handler;

import com.alibaba.fastjson2.JSON;
import com.zs.common.core.HttpEnum;
import com.zs.common.core.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义未登录处理
 * @author 86740
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        String s = JSON.toJSONString(new Result().error(HttpEnum.UNAUTHORIZED));
        response.getWriter().println(s);
    }
}
