package com.zs.common.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.zs.common.constant.Constants;
import com.zs.common.exception.ZsException;
import com.zs.common.model.LoginUserInfo;
import com.zs.common.model.SysUser;
import com.zs.common.redis.RedisUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {


    // 秘钥
    private static final String secret = "mygege";
    // 有效时间 毫秒
    private static final Long expire = 24 * 60 * 60 * 1000L;
    // 用户凭证
    private static final String header = "Authorization";
    // 签发者
    private static final String issuer = "zs.com";

    // 随机一个key
    static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Autowired
    private RedisUtil redisUtil;


    /**
     * 生成token签名
     *
     * @param
     * @return
     */
    public  String createToken(LoginUserInfo loginUserInfo) {

        //header参数
        final Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS256");
        headerMap.put("typ", "JWT");
        System.out.println(key.getFormat());
        System.out.println(DateUtil.formatDateTime(new Date(System.currentTimeMillis() + expire)) );
        //生成token
        String token = Jwts.builder()
                .setHeader(headerMap)
                .setSubject(Constants.LOGIN_INFO + loginUserInfo.sysUser.getSysUserId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .setIssuer(issuer)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
//        redisUtil.setObject(Constants.LOGIN_INFO  + loginUserInfo.getSysUser().getSysUserId(), loginUserInfo, expire, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 解析token
     *
     * @param token token
     * @return
     */
    public  Claims parseToken(String token) {
        System.out.println(key.getFormat());
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (Exception e) {
            return null;
        }


//        try {
//            claims = Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (ExpiredJwtException e) {
//            throw new RuntimeException("JWT过期");
//        } catch (UnsupportedJwtException e) {
//            throw new RuntimeException("不支持的JWT");
//        } catch (MalformedJwtException e) {
//            throw new RuntimeException("JWT格式错误");
//        } catch (SignatureException e) {
//            throw   new RuntimeException("签名异常"); // "签名异常"
//        } catch (IllegalArgumentException e) {
//            throw new RuntimeException("非法请求");
//        } catch (Exception e) {
//            throw new RuntimeException("解析异常");
//        }
//        System.out.println("*******" + token);
//        return claims;
    }




    /**
     * 判断token是否过期
     *
     * @param expiration
     * @return
     */
    public boolean isExpired(Date expiration) {
        return expiration.before(new Date());
    }

//    public static void main(String[] args) {
//        LoginUserInfo loginUserInfo = new LoginUserInfo();
//        SysUser sysUser = new SysUser();
//        sysUser.setSysUserId(1111111L);
//        loginUserInfo.setSysUser(sysUser);
//        String token = createToken(loginUserInfo);
//        System.out.println("*******" + token);
//        Claims claims = parseToken(token);
//        System.out.println(claims);
//    }



}
