package com.zs.modules.sys.log.controller;


import cn.hutool.core.bean.BeanUtil;
import com.zs.common.core.core.Result;
import com.zs.common.core.excel.ExcelUtils;
import com.zs.common.core.log.params.SysLogLoginAddParams;
import com.zs.common.core.page.PageResult;
import com.zs.modules.sys.log.domain.excel.SysLogLoginExcel;
import com.zs.modules.sys.log.domain.params.SysLogLoginQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogLoginVo;
import com.zs.modules.sys.log.service.ISysLogLoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public Result<List<SysLogLoginVo>> todayList() {
        List<SysLogLoginVo> list = iSysLogLoginService.todayList();
        return new Result<List<SysLogLoginVo>>().ok(list);
    }

    @PostMapping
    public Result<?> save(@RequestBody SysLogLoginAddParams sysLogLoginAddParams) {
        iSysLogLoginService.save(sysLogLoginAddParams);
        return new Result<>().ok();
    }

    @GetMapping("export")
    @PreAuthorize("hasAuthority('sys:loglogin:export')")
    public void export(HttpServletResponse response, SysLogLoginQueryParams sysLogLoginQueryParams) throws IOException {
        List<SysLogLoginVo> list = iSysLogLoginService.list(sysLogLoginQueryParams);
        List<SysLogLoginExcel> excelList = BeanUtil.copyToList(list, SysLogLoginExcel.class);
        ExcelUtils.exportExcel(response, "登录日志.xlsx", SysLogLoginExcel.class, excelList);
    }


}
