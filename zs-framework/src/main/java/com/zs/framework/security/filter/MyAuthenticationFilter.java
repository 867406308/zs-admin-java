package com.zs.framework.security.filter;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * 用户名密码过滤器
 */

public class MyAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * 是否 仅仅post方式
     */
    private boolean postOnly = true;
    private String usernameParameter = "username";
    private String passwordParameter = "password";

    public MyAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    /**
     * 通过 传入的 参数 创建 匹配器
     * 即 Filter过滤的url
     */
    public MyAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            JSONObject jsonObject = getBodyParams(request);


            String username = jsonObject.getStr(this.usernameParameter);
            String password = jsonObject.getStr(this.passwordParameter);
            if (!StringUtils.hasText(username)) {
                throw new RuntimeException("用户名不能为空!");
            }
            if (!StringUtils.hasText(password)) {
                throw new RuntimeException("密码不能为空!");
            }
            // 该处对第一步的token进行包装，用于在AuthenticationProvider里面校验是否该AuthenticationProvider拦截校验
            // 此处从请求中获取所有的参数和请求头放入UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
            this.setDetails(request, token);
            return this.getAuthenticationManager().authenticate(token);
        }
    }


    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken token) {
        token.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


    public JSONObject getBodyParams(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        String requestBody = IoUtil.read(inputStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = JSONUtil.parseObj(requestBody);
        return  jsonObject;
    }

}
