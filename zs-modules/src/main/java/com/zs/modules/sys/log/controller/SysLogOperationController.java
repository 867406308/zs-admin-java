package com.zs.modules.sys.log.controller;


import cn.hutool.core.bean.BeanUtil;
import com.zs.common.core.core.Result;
import com.zs.common.core.excel.ExcelUtils;
import com.zs.common.core.log.params.SysLogOperationAddParams;
import com.zs.common.core.page.PageResult;
import com.zs.modules.sys.log.domain.excel.SysLogOperationExcel;
import com.zs.modules.sys.log.domain.params.SysLogOperationQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogOperationVo;
import com.zs.modules.sys.log.service.ISysLogOperationService;
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

    @GetMapping("export")
    @PreAuthorize("hasAuthority('sys:logOperation:export')")
    public void export(HttpServletResponse response, SysLogOperationQueryParams sysLogOperationQueryParams) throws IOException {
        List<SysLogOperationVo> list = iSysLogOperationService.list(sysLogOperationQueryParams);
        List<SysLogOperationExcel> excelList = BeanUtil.copyToList(list, SysLogOperationExcel.class);
        ExcelUtils.exportExcel(response, "操作日志.xlsx", SysLogOperationExcel.class, excelList);
    }
}
