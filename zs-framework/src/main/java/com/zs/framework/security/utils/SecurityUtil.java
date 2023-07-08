package com.zs.framework.security.utils;

import com.zs.common.model.LoginUserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * springSecurity工具类
 */
public class SecurityUtil {


    public static LoginUserInfo getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LoginUserInfo) authentication.getPrincipal();
    }


}
