package com.zs.common.security.handler;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zs.common.aop.annotation.LoginLog;
import com.zs.common.core.constant.RedisConstants;
import com.zs.common.core.core.Result;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.model.LoginUserInfo;
import com.zs.common.core.model.SysUser;
import com.zs.common.core.utils.CryptoUtil;
import com.zs.common.core.utils.IpUtils;
import com.zs.common.redis.config.RedisUtil;
import com.zs.common.security.model.TokenVo;
import com.zs.common.security.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Resource
    private ObjectMapper objectMapper;

    @LoginLog
    @Override
    public void onAuthenticationSuccess(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Authentication authentication) throws IOException {
        LoginUserInfo loginUserInfo = (LoginUserInfo) authentication.getPrincipal();
        // 登录成功把用户信息存入redis,用来表示在线用户。
        setUserInfoToRedis(request, loginUserInfo.getSysUser());
        // 保存加密的key
        saveCryptoKeyToRedis(request, loginUserInfo.getSysUser());

        TokenVo tokenVo = new TokenVo();
        tokenVo.setAccessToken(jwtUtil.createToken(loginUserInfo));
        tokenVo.setRefreshToken("");
        Result<Object> result = new Result<>().ok(200, "登录成功", tokenVo);
        String responseBody = objectMapper.writeValueAsString(result);

        // Write to the response
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(responseBody);

    }
    private void saveCryptoKeyToRedis(HttpServletRequest request, SysUser sysUser) {
        String cryptoKey = request.getHeader("cryptoKey");
        if (cryptoKey == null) {
            throw new ZsException("请求头cryptoKey 不能为空");
        }
        String decryptedKey = CryptoUtil.sm2Decrypt(cryptoKey).replace("\"", "");
        redisUtil.setObject(RedisConstants.SM4_KEY + sysUser.getSysUserId(), decryptedKey);
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
