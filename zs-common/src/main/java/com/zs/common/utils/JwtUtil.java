package com.zs.common.utils;

import com.zs.common.constant.Constants;
import com.zs.common.model.LoginUserInfo;
import com.zs.common.redis.RedisUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * @description
 * @author 86740
 **/

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // 从配置文件中读取密钥
    private String secret;

    @Value("${jwt.expiration}") // 从配置文件中读取过期时间（单位：分钟）
    private Long expirationTime;

//    // 有效时间 毫秒
//    private static final Long EXPIRATION_TIME = 24 * 60 * 60 * 1000L; // 24小时
//    // 自动续期时间 毫秒
//    private static final long AUTO_RENEWAL_TIME = 30 * 60 * 1000L; // 30分钟
    // 签发者
    private static final String ISSUER = "zsAdmin.com";
    @Resource
    private RedisUtil redisUtil;
    private SecretKey secretKey;
    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] keyBytes = new byte[32];
            secureRandom.nextBytes(keyBytes);
            System.out.println("随机生成的密钥（Base64编码）: " + java.util.Base64.getEncoder().encodeToString(keyBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 生成token签名
     *
     * @params loginUserInfo
     * @return String
     */
    public String createToken(LoginUserInfo loginUserInfo) {
        //header参数
        final Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS256");
        headerMap.put("typ", "JWT");
        //生成token
        String token = Jwts.builder()
                .setHeader(headerMap)
                .setSubject(Constants.LOGIN_INFO + loginUserInfo.sysUser.getSysUserId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setIssuer(ISSUER)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        redisUtil.setObject(Constants.LOGIN_INFO + loginUserInfo.getSysUser().getSysUserId(), loginUserInfo, expirationTime, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 解析token
     *
     * @param token token
     * @return Claims
     */
    public Claims parseToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (Exception e) {
            return null;
        }
    }




}