package com.zs.quartz.controller;

import com.zs.common.aop.annotation.Log;
import com.zs.common.core.core.Result;
import com.zs.common.core.enums.OperationTypeEnum;
import com.zs.common.core.page.PageResult;
import com.zs.quartz.domain.params.SysJobAddParams;
import com.zs.quartz.domain.params.SysJobQueryParams;
import com.zs.quartz.domain.params.SysJobUpdateParams;
import com.zs.quartz.domain.vo.SysJobVo;
import com.zs.quartz.service.ISysJobService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务
 */
@RestController
@RequestMapping("system/sys/job")
public class SysJobController {

    @Resource
    private  ISysJobService isSysJobService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:job:page')")
    public Result<PageResult<SysJobVo>> page(SysJobQueryParams sysJobQueryParams) {
        PageResult<SysJobVo> iPage = isSysJobService.page(sysJobQueryParams);
        return new Result<PageResult<SysJobVo>>().ok(iPage);
    }

    @Log(module = "定时任务-新增定时任务", type = OperationTypeEnum.ADD, description = "新增定时任务信息")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:job:save')")
    public Result<?> save(@RequestBody SysJobAddParams sysJobAddParams) {
        isSysJobService.save(sysJobAddParams);
        return new Result<>().ok();
    }

    @Log(module = "定时任务-修改定时任务", type = OperationTypeEnum.UPDATE, description = "修改定时任务信息")
    @PutMapping("update")
    @PreAuthorize("hasAuthority('sys:job:update')")
    public Result<?> update(@RequestBody SysJobUpdateParams sysJobUpdateParams) {
        isSysJobService.update(sysJobUpdateParams);
        return new Result<>().ok();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:job:info')")
    public Result<SysJobVo> get(@PathVariable("id") Long id) {
        SysJobVo sysJobVo = isSysJobService.get(id);
        return new Result<SysJobVo>().ok(sysJobVo);
    }

    @Log(module = "定时任务-删除定时任务", type = OperationTypeEnum.DELETE, description = "删除定时任务信息")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('sys:job:delete')")
    public Result<?> delete(@PathVariable("id") Long id) {
        isSysJobService.del(id);
        return new Result<>().ok();
    }

    @PostMapping("pause")
    @PreAuthorize("hasAuthority('sys:job:pause')")
    public Result<?> pause(@RequestBody SysJobUpdateParams sysJobUpdateParams) {
        isSysJobService.pause(sysJobUpdateParams);
        return new Result<>().ok();
    }

    @PostMapping("resume")
    @PreAuthorize("hasAuthority('sys:job:resume')")
    public Result<?> resume(@RequestBody SysJobUpdateParams sysJobUpdateParams) {
        isSysJobService.resume(sysJobUpdateParams);
        return new Result<>().ok();
    }

    @PostMapping("run")
    @PreAuthorize("hasAuthority('sys:job:run')")
    public Result<?> run(@RequestBody SysJobUpdateParams sysJobUpdateParams) {
        isSysJobService.run(sysJobUpdateParams);
        return new Result<>().ok();
    }

}
