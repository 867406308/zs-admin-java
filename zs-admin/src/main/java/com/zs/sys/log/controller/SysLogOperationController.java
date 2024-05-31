package com.zs.sys.log.controller;


import com.zs.common.core.core.Result;
import com.zs.common.core.log.params.SysLogOperationAddParams;
import com.zs.common.core.page.PageResult;
import com.zs.sys.log.domain.params.SysLogOperationQueryParams;
import com.zs.sys.log.domain.vo.SysLogOperationVo;
import com.zs.sys.log.service.ISysLogOperationService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @author 86740
 */
@RestController
@RequestMapping("system/sys/log/operation")
public class SysLogOperationController {


    @Resource
    private ISysLogOperationService iSysLogOperationService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:logOperation:page')")
    public Result<PageResult<SysLogOperationVo>> page(SysLogOperationQueryParams sysLogOperationQueryParams) {
        PageResult<SysLogOperationVo> iPage = iSysLogOperationService.page(sysLogOperationQueryParams);
        return new Result<PageResult<SysLogOperationVo>>().ok(iPage);
    }

    @PostMapping
    public Result<?> save(@RequestBody SysLogOperationAddParams sysLogOperationAddParams) {
        iSysLogOperationService.save(sysLogOperationAddParams);
        return new Result<>().ok();
    }
}
