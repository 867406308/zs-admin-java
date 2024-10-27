package com.zs.quartz.controller;

import com.zs.common.core.core.Result;
import com.zs.common.core.page.PageResult;
import com.zs.quartz.domain.params.SysJobLogQueryParams;
import com.zs.quartz.domain.vo.SysJobLogVo;
import com.zs.quartz.service.ISysJobLogService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
@RequestMapping("system/sys/job/log")
public class SysJobLogController {

    @Resource
    private ISysJobLogService sysJobLogService;

    @GetMapping("page")
    public Result<PageResult<SysJobLogVo>> page(SysJobLogQueryParams sysJobLogQueryParams) {
        PageResult<SysJobLogVo> iPage = sysJobLogService.page(sysJobLogQueryParams);
        return new Result<PageResult<SysJobLogVo>>().ok(iPage);
    }
}
