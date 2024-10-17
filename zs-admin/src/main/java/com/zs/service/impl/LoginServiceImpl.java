package com.zs.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.zs.common.core.constant.RedisConstants;
import com.zs.common.core.model.LoginUserInfo;
import com.zs.common.core.model.SysUser;
import com.zs.common.core.utils.CryptoUtil;
import com.zs.common.core.utils.IpUtils;
import com.zs.common.redis.config.RedisUtil;
import com.zs.common.security.model.TokenVo;
import com.zs.common.security.utils.JwtUtil;
import com.zs.domain.params.LoginParams;
import com.zs.service.ILoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public TokenVo login(LoginParams loginParams, HttpServletRequest request) {

        if (!StringUtils.hasText(loginParams.getUsername())) {
            throw new RuntimeException("用户名不能为空!");
        }
        if (!StringUtils.hasText(loginParams.getPassword())) {
            throw new RuntimeException("密码不能为空!");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginParams.getUsername(), loginParams.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);
        LoginUserInfo loginUserInfo = (LoginUserInfo) authentication.getPrincipal();


        TokenVo tokenVo = new TokenVo();
        tokenVo.setAccessToken(jwtUtil.createToken(loginUserInfo));

        // 登录成功把用户信息存入redis,用来表示在线用户。
        setUserInfoToRedis(request, loginUserInfo.getSysUser());

        // redis中保存登录成功后的sm4密钥
        redisUtil.setObject(RedisConstants.SM4_KEY + loginUserInfo.getSysUser().getSysUserId(), CryptoUtil.sm2Decrypt(request.getHeader("cryptoKey")).replace("\"", ""));

        return tokenVo;
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
