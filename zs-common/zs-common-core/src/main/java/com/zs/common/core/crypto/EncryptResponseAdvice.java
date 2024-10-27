package com.zs.common.core.crypto;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zs.common.core.constant.RedisConstants;
import com.zs.common.core.core.Result;
import com.zs.common.core.crypto.annotation.Encryption;
import com.zs.common.core.enums.CryptoTypeEnum;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.utils.CryptoUtil;
import com.zs.common.core.utils.SecurityUtil;
import com.zs.common.redis.config.RedisUtil;
import io.micrometer.common.lang.NonNullApi;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 返回值加密
 */
@RestControllerAdvice
@NonNullApi
public class EncryptResponseAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private RedisUtil redisUtil;

//    private static final Set<String> IGNORE_URL_SET = new HashSet<>();
//    static {
//        IGNORE_URL_SET.add("/auth/login");
//    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {


//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if(attributes == null){
//            return false;
//        }
//        HttpServletRequest request = attributes.getRequest();
//        String path = request.getRequestURI();
//
//        // 检查是否在忽略列表中
//        if (IGNORE_URL_SET.contains(path)) {
//            return true;
//        }

        // 检查是否包含加密注解
        return returnType.hasMethodAnnotation(Encryption.class);
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        System.out.println("需要加密的报文： " + body);
        //
//        if(IGNORE_URL_SET.contains(request.getURI().getPath())){
//           HttpHeaders headers = request.getHeaders();
//           String  sm4Key = Objects.requireNonNull(headers.get("cryptoKey")).toString();
//           redisUtil.setKey(RedisConstants.SM4_KEY + request.getURI().getPath(), sm4Key);
//        }
        Encryption encryption = returnType.getMethodAnnotation(Encryption.class);
        if (encryption == null) {
            return body;
        }
        Result<?> result = (Result<?>) body;
        Object data = Objects.requireNonNull(result).getData();
        try {
            if(encryption.value() == CryptoTypeEnum.SM4){
                String sm4Key = Objects.requireNonNull(redisUtil.get(RedisConstants.SM4_KEY + SecurityUtil.getUserInfo().getSysUser().getSysUserId())).toString();
                Object encryptedData = CryptoUtil.sm4Encrypt(data == null ? "" : objectMapper.writeValueAsString(data), sm4Key);
                // 设置加密后的数据
                result.setData(encryptedData);
            }else if(encryption.value() == CryptoTypeEnum.SM2){
                Object encryptedData = CryptoUtil.sm2Encrypt(data == null ? "" : objectMapper.writeValueAsString(data));
                // 设置加密后的数据
                result.setData(encryptedData);
            }
            return result;
        } catch (Exception e) {
            throw new ZsException("加密失败");
        }
    }

}
