package com.zs.sys.log.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zs.common.core.core.Result;
import com.zs.common.core.log.params.SysLogLoginAddParams;
import com.zs.common.core.page.PageResult;
import com.zs.sys.log.domain.params.SysLogLoginQueryParams;
import com.zs.sys.log.domain.vo.SysLogLoginVo;
import com.zs.sys.log.service.ISysLogLoginService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 86740
 */
@RestController
@RequestMapping("system/sys/log/login")
public class SysLogLoginController {


    @Resource
    private ISysLogLoginService iSysLogLoginService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:loglogin:page')")
    public Result<PageResult<SysLogLoginVo>> page(SysLogLoginQueryParams sysLogLoginQueryParams) {
        PageResult<SysLogLoginVo> iPage = iSysLogLoginService.page(sysLogLoginQueryParams);
        return new Result<PageResult<SysLogLoginVo>>().ok(iPage);
    }


    @GetMapping("todayList")
    @PreAuthorize("hasAuthority('sys:loglogin:list')")
    public Result<List<SysLogLoginVo>> todayList() {
        List<SysLogLoginVo> list = iSysLogLoginService.todayList();
        return new Result<List<SysLogLoginVo>>().ok(list);
    }

    @PostMapping
    public Result<?> save(@RequestBody SysLogLoginAddParams sysLogLoginAddParams) {
        iSysLogLoginService.save(sysLogLoginAddParams);
        return new Result<>().ok();
    }

}
