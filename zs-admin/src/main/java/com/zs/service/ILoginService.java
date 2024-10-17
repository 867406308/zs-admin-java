package com.zs.service;

import com.zs.common.core.core.Result;
import com.zs.common.security.model.TokenVo;
import com.zs.domain.params.LoginParams;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author zs
 */
public interface ILoginService {

    TokenVo login(LoginParams loginParams, HttpServletRequest request);
}
