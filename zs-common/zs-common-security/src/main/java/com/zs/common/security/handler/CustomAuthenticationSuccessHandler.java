package com.zs.common.security.handler;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.zs.common.aop.annotation.LoginLog;
import com.zs.common.core.constant.RedisConstants;
import com.zs.common.core.core.Result;
import com.zs.common.core.model.LoginUserInfo;
import com.zs.common.core.model.SysUser;
import com.zs.common.core.utils.IpUtils;
import com.zs.common.redis.config.RedisUtil;
import com.zs.common.security.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义登录成功处理
 *
 * @author 86740
 */

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisUtil redisUtil;

    @LoginLog
    @Override
    public void onAuthenticationSuccess(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Authentication authentication) throws IOException {
        LoginUserInfo loginUserInfo = (LoginUserInfo) authentication.getPrincipal();
        // 登录成功把用户信息存入redis,用来表示在线用户。
        setUserInfoToRedis(request, loginUserInfo.getSysUser());

        response.setContentType("application/json;charset=UTF-8");
        String s = JSONUtil.toJsonStr(new Result<>().ok(200, "登录成功", jwtUtil.createToken(loginUserInfo)));
        response.getWriter().println(s);

    }

    @Async
    public void setUserInfoToRedis(@NotNull HttpServletRequest request, @NotNull SysUser sysUser){
        String userAgentString = request.getHeader(HttpHeaders.USER_AGENT);
        UserAgent userAgent = UserAgentUtil.parse(userAgentString);
        String ipAddr = IpUtils.getIpAddr(request);

        Map<String, Object> map = new HashMap<>();
        map.put("sysUserId", sysUser.getSysUserId());
        map.put("username", sysUser.getUsername());
        map.put("realName", sysUser.getRealName());
        map.put("avatar", sysUser.getAvatar());
        map.put("lastLoginTime", sysUser.getLastLoginTime());
        map.put("ip", ipAddr);
        map.put("city", IpUtils.getCityInfo(ipAddr));
        map.put("browser", userAgent.getBrowser().toString());
        map.put("os", userAgent.getOs().toString());

        redisUtil.setObject(RedisConstants.ONLINE_USER + sysUser.getSysUserId(), map);
    }
}
