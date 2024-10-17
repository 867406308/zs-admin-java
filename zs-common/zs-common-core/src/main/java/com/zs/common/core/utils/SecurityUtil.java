package com.zs.common.core.utils;


import com.zs.common.core.model.LoginUserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * springSecurity工具类
 *
 * @author 86740
 */
public class SecurityUtil {


    public static LoginUserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LoginUserInfo) authentication.getPrincipal();
    }






}
