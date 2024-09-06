package com.zs.sys.config.controller;

import com.zs.common.aop.annotation.Log;
import com.zs.common.core.core.Result;
import com.zs.common.core.enums.OperationTypeEnum;
import com.zs.sys.config.domain.params.SysConfigParams;
import com.zs.sys.config.domain.vo.*;
import com.zs.sys.config.service.ISysConfigService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置
 */
@RestController
@RequestMapping("system/sys/config")
public class SysConfigController {

    @Resource
    private ISysConfigService iSysConfigService;

    @Log(module = "系统配置-修改", type = OperationTypeEnum.UPDATE, description = "修改系统配置")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:config:update')")
    public Result<?> update(@RequestBody SysConfigParams sysConfigParams){
        iSysConfigService.update(sysConfigParams);
        return new Result<>().ok();
    }

    @GetMapping("website")
    @PreAuthorize("hasAuthority('sys:config:info')")
    public Result<SysConfigWebsiteVo> websiteInfo(){
        SysConfigWebsiteVo sysConfigWebsiteVo = iSysConfigService.websiteInfo();
        return new Result<SysConfigWebsiteVo>().ok(sysConfigWebsiteVo);
    }
    @GetMapping("file")
    @PreAuthorize("hasAuthority('sys:config:info')")
    public Result<SysConfigFileVo> fileUploadInfo(){
        SysConfigFileVo sysConfigFileVo = iSysConfigService.fileUploadInfo();
        return new Result<SysConfigFileVo>().ok(sysConfigFileVo);
    }

    @GetMapping("sms")
    @PreAuthorize("hasAuthority('sys:config:info')")
    public Result<SysConfigSmsVo> smsInfo(){
        SysConfigSmsVo sysConfigSmsVo = iSysConfigService.smsInfo();
        return new Result<SysConfigSmsVo>().ok(sysConfigSmsVo);
    }

    @GetMapping("email")
    @PreAuthorize("hasAuthority('sys:config:info')")
    public Result<SysConfigEmailVo> emailInfo(){
        SysConfigEmailVo sysConfigEmailVo = iSysConfigService.emailInfo();
        return new Result<SysConfigEmailVo>().ok(sysConfigEmailVo);
    }

    @GetMapping("pay")
    @PreAuthorize("hasAuthority('sys:config:info')")
    public Result<SysConfigPayVo> payInfo(){
        SysConfigPayVo sysConfigPayVo = iSysConfigService.payInfo();
        return new Result<SysConfigPayVo>().ok(sysConfigPayVo);
    }

    @GetMapping("other")
    @PreAuthorize("hasAuthority('sys:config:info')")
    public Result<SysConfigOtherVo> otherInfo(){
        SysConfigOtherVo sysConfigOtherVo = iSysConfigService.otherInfo();
        return new Result<SysConfigOtherVo>().ok(sysConfigOtherVo);
    }
}
