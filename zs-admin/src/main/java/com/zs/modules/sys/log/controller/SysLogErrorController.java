package com.zs.modules.sys.log.controller;

import com.zs.common.core.Result;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.log.domain.params.SysLogErrorQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogErrorVo;
import com.zs.modules.sys.log.service.ISysLogErrorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 86740
 */
@RestController
@RequestMapping("sys/log/error")
public class SysLogErrorController {


    @Resource
    private ISysLogErrorService iSysLogErrorService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:logerror:page')")
    public Result page(SysLogErrorQueryParams sysLogErrorQueryParams){
        PageResult<SysLogErrorVo> iPage =  iSysLogErrorService.page(sysLogErrorQueryParams);
        return new Result().ok(iPage);
    }

}
