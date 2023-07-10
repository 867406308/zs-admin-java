package com.zs.framework.security.handler;

import com.alibaba.fastjson2.JSON;
import com.zs.common.core.HttpEnum;
import com.zs.common.core.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义权限不足过滤器
 *
 * @author 86740
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String s = JSON.toJSONString(new Result().error(HttpEnum.FORBIDDEN));
        response.getWriter().println(s);
    }
}
