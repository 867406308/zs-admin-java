package com.zs.common.core.utils;

import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 * @author 86740
 */
public class StrUtils {


    public static boolean isMatch(List<String> patterns, String path) {
        AntPathMatcher antMatcher = new AntPathMatcher();
        return patterns.stream().anyMatch(s -> antMatcher.match(s, path));
    }


}
