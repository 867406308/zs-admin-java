package com.zs.service;

import com.zs.domain.params.LoginParams;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author zs
 */
public interface ILoginService {

    void login(LoginParams loginParams, HttpServletRequest request, HttpServletResponse response);
}
