package com.zs.modules.sys.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zs.common.core.Result;
import com.zs.common.model.LoginUserInfo;
import com.zs.framework.security.utils.SecurityUtil;
import com.zs.modules.sys.user.domain.dto.SysUserDTO;
import com.zs.modules.sys.user.domain.entity.SysUserEntity;
import com.zs.modules.sys.user.domain.vo.SysUserVo;
import com.zs.modules.sys.user.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("sys/user")
public class SysUserController {


    @Resource
    private ISysUserService iSysUserService;

    @PostMapping("save")
    public void save(@RequestBody SysUserVo sysUserVo){

         iSysUserService.save(BeanUtil.copyProperties(sysUserVo, SysUserDTO.class));
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(){
        LoginUserInfo loginUserInfo = SecurityUtil.getUserInfo();

          return new Result().ok(loginUserInfo);
    }
}
