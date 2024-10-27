package com.zs.common.security.filter;

import cn.hutool.json.JSONUtil;
import com.zs.common.core.constant.Constants;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.model.LoginUserInfo;
import com.zs.common.core.utils.StrUtils;
import com.zs.common.redis.config.RedisUtil;
import com.zs.common.security.propetties.WhiteUrlProperties;
import com.zs.common.security.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.micrometer.common.lang.NonNullApi;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * token认证过滤器
 *
 * @author 86740
 */
@Component
@NonNullApi
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private WhiteUrlProperties whiteUrlProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 自定义方法，判断是否为静态资源请求
        if (isStaticResourceRequest(request)) {
            chain.doFilter(request, response); // 直接传递给下一个过滤器
            return;
        }
        // 检查URL是否在白名单内
        if (StrUtils.isMatch(whiteUrlProperties.getUrl(), request.getServletPath())) {
            chain.doFilter(request, response);
            return;
        }


        // 验证授权信息
        if (!StringUtils.hasText(request.getHeader(HttpHeaders.AUTHORIZATION))) {
            chain.doFilter(request, response);
            return;
        }

        // 解析Token并验证用户信息
        LoginUserInfo loginUserInfo = authenticate(request);

        // 设置认证信息
        setAuthentication(Objects.requireNonNull(loginUserInfo));

        // 放行
        chain.doFilter(request, response);
    }

    @Nullable
    private LoginUserInfo authenticate(@NotNull HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).replace(Constants.TOKEN_PREFIX, "");
        Claims claims = jwtUtil.parseToken(token);
        if (Objects.isNull(claims)) {
            throw new ZsException("Invalid token");
        }
        String loginInfo = claims.getSubject();
        Object jsonLoginUserInfo = redisUtil.get(loginInfo);

        return JSONUtil.toBean(JSONUtil.parseObj(jsonLoginUserInfo), LoginUserInfo.class);
    }

    private void setAuthentication(@NotNull LoginUserInfo loginUserInfo) {
        // 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserInfo, null, loginUserInfo.getAuthorities());
        // 存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private boolean isStaticResourceRequest(@NotNull HttpServletRequest request) {
        String uri = request.getRequestURI();
        // 根据实际情况定义哪些是静态资源的路径前缀
        return uri.startsWith("/static/") || uri.endsWith(".css") || uri.endsWith(".js") /* 更多静态资源后缀 */;
    }
}
