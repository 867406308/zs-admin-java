package com.zs.modules.sys.log.controller;

import com.zs.common.core.Result;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.log.domain.params.SysLogLoginQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogLoginVo;
import com.zs.modules.sys.log.service.ISysLogLoginService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("sys/log/login")
public class SysLogLoginController {


    @Resource
    private ISysLogLoginService iSysLogLoginService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:loglogin:page')")
    public Result page(SysLogLoginQueryParams sysLogLoginQueryParams) {
        PageResult<SysLogLoginVo> iPage = iSysLogLoginService.page(sysLogLoginQueryParams);
        return new Result().ok(iPage);
    }

}
