package com.zs.modules.sys.log.controller;

import com.zs.common.core.Result;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.log.domain.params.SysLogOperationQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogOperationVo;
import com.zs.modules.sys.log.service.ISysLogOperationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("sys/log/operation")
public class SysLogOperationController {


    @Resource
    private ISysLogOperationService iSysLogOperationService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:logoperation:page')")
    public Result page(SysLogOperationQueryParams sysLogOperationQueryParams) {
        PageResult<SysLogOperationVo> iPage = iSysLogOperationService.page(sysLogOperationQueryParams);
        return new Result().ok(iPage);
    }

}
