package com.zs.framework.security.filter;

import cn.hutool.json.JSONUtil;
import com.zs.common.constant.Constants;
import com.zs.common.model.LoginUserInfo;
import com.zs.common.redis.RedisUtil;
import com.zs.common.utils.JwtUtil;
import com.zs.framework.security.propetties.WhiteUrlProperties;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * token认证过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter   {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;
    @Resource
    private WhiteUrlProperties whiteUrlProperties;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String Authorization = request.getHeader(Constants.TOKEN_PREFIX);
        if(whiteUrlProperties.getUrl().contains(request.getServletPath()) || !StringUtils.hasText(Authorization)){
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(Constants.TOKEN_PREFIX).replace("Bearer ","");
        Claims claims = jwtUtil.parseToken(token);
        if(Objects.isNull(claims)){
//            throw new AccessDeniedException("TOKEN不存在，请重新登录！");

            chain.doFilter(request, response);
            return;
        }
        String loginInfo = claims.getSubject();
        // redis中获取token用户信息
        Object object = redisUtil.get(loginInfo);
        LoginUserInfo loginUserInfo = JSONUtil.toBean(JSONUtil.parseObj(redisUtil.get(loginInfo)), LoginUserInfo.class);
        // 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserInfo, null, null);
        // 存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        chain.doFilter(request, response);
    }


}
