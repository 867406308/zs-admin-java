package com.zs.framework.security.utils;

import com.zs.common.model.LoginUserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * springSecurity工具类
 * @author 86740
 */
public class SecurityUtil {


    public static LoginUserInfo getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LoginUserInfo) authentication.getPrincipal();
    }


}
