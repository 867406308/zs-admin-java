package com.zs.controller;

import com.zs.common.core.crypto.annotation.Decryption;
import com.zs.common.core.enums.CryptoTypeEnum;
import com.zs.domain.params.LoginParams;
import com.zs.service.ILoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @Decryption(value = CryptoTypeEnum.SM2)
    @PostMapping("login")
    public void login(@RequestBody LoginParams loginParams, HttpServletRequest request, HttpServletResponse response) {
         loginService.login(loginParams, request, response);
    }
}
