package com.zs.sys.log.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zs.common.core.core.Result;
import com.zs.common.core.excel.ExcelUtils;
import com.zs.common.core.log.params.SysLogErrorAddParams;
import com.zs.common.core.page.PageResult;
import com.zs.sys.log.domain.excel.SysLogErrorExcel;
import com.zs.sys.log.domain.params.SysLogErrorQueryParams;
import com.zs.sys.log.domain.vo.SysLogErrorVo;
import com.zs.sys.log.service.ISysLogErrorService;
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
@RequestMapping("system/sys/log/error")
public class SysLogErrorController {


    @Resource
    private ISysLogErrorService iSysLogErrorService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:logerror:page')")
    public Result<PageResult<SysLogErrorVo>> page(SysLogErrorQueryParams sysLogErrorQueryParams) {
        PageResult<SysLogErrorVo> iPage = iSysLogErrorService.page(sysLogErrorQueryParams);
        return new Result<PageResult<SysLogErrorVo>>().ok(iPage);
    }

    @PostMapping
    public Result<?> save(@RequestBody SysLogErrorAddParams sysLogErrorAddParams) {
        iSysLogErrorService.save(sysLogErrorAddParams);
        return new Result<>().ok();
    }

    @GetMapping("export")
    @PreAuthorize("hasAuthority('sys:logerror:export')")
    public void export(HttpServletResponse response, SysLogErrorQueryParams sysLogErrorQueryParams) throws IOException {
        List<SysLogErrorVo> list = iSysLogErrorService.list(sysLogErrorQueryParams);
        List<SysLogErrorExcel> excelList = BeanUtil.copyToList(list, SysLogErrorExcel.class);
        ExcelUtils.exportExcel(response, "异常日志.xlsx", SysLogErrorExcel.class, excelList);

    }

}
