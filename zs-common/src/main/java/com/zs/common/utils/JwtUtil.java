package com.zs.common.utils;

import com.zs.common.constant.Constants;
import com.zs.common.model.LoginUserInfo;
import com.zs.common.redis.RedisUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private static final String issuer = "zsAdmin.com";
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
     * @param
     * @return
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
                .setIssuer(issuer)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        redisUtil.setObject(Constants.LOGIN_INFO + loginUserInfo.getSysUser().getSysUserId(), loginUserInfo, expirationTime, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 解析token
     *
     * @param token token
     * @return
     */
    public Claims parseToken(String token) {
        Claims claims = null;
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

    /**
     * 验证token是否正确
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检查并更新令牌的过期时间
     *
     * @param token 令牌
     * @return 更新后的令牌
     */
    public String checkAndRenewToken(String token) {
        Claims claims = parseToken(token);
        if (claims != null && isTokenExpired(claims)) {
            // 计算新的过期时间
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime newExpirationTime = currentTime.plus(expirationTime, ChronoUnit.MILLIS);
            Date newExpirationDate = Date.from(newExpirationTime.atZone(ZoneId.systemDefault()).toInstant());

            // 更新令牌的过期时间
            claims.setExpiration(newExpirationDate);

            // 重新生成令牌
            String renewedToken = Jwts.builder()
                    .setClaims(claims)
                    .signWith(secretKey, SignatureAlgorithm.HS256)
                    .compact();

            // 更新Redis中的令牌过期时间
            redisUtil.setObject(Constants.JWT_KEY, renewedToken, expirationTime, TimeUnit.MILLISECONDS);

            return renewedToken;
        }

        return token;
    }

    /**
     * 检查令牌是否过期
     *
     * @param claims 令牌的Claims对象
     * @return true表示令牌已过期，false表示令牌未过期
     */
    private boolean isTokenExpired(Claims claims) {
        Date expirationDate = claims.getExpiration();
        return expirationDate != null && expirationDate.before(new Date());
    }

}