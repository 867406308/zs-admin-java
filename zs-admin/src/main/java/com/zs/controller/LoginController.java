package com.zs.controller;

import com.zs.common.core.core.Result;
import com.zs.common.core.crypto.annotation.Decryption;
import com.zs.common.core.crypto.annotation.Encryption;
import com.zs.common.core.enums.CryptoTypeEnum;
import com.zs.common.security.model.TokenVo;
import com.zs.domain.params.LoginParams;
import com.zs.service.ILoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zs
 */
@RestController
@RequestMapping("auth")
public class LoginController {

    @Resource
    private ILoginService loginService;

//    @Decryption(value = CryptoTypeEnum.SM2)
    @PostMapping("login")
    public Result<TokenVo> login(@RequestBody LoginParams loginParams, HttpServletRequest request) {
         return new Result<TokenVo>().ok(loginService.login(loginParams, request));
    }
}
