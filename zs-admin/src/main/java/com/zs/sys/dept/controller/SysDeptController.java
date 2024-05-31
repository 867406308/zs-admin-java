package com.zs.sys.dept.controller;


import com.zs.common.aop.annotation.Log;
import com.zs.common.core.core.Result;
import com.zs.common.core.enums.OperationTypeEnum;
import com.zs.sys.dept.domain.params.SysDeptAddParams;
import com.zs.sys.dept.domain.params.SysDeptQueryParams;
import com.zs.sys.dept.domain.vo.SysDeptVo;
import com.zs.sys.dept.service.ISysDeptService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 86740
 */
@RestController
@RequestMapping("system/sys/dept")
public class SysDeptController {

    @Resource
    private ISysDeptService iSysDeptService;


    @GetMapping("tree")
    @PreAuthorize("hasAuthority('sys:dept:tree')")
    public Result<List<SysDeptVo>> list(SysDeptQueryParams sysOrgQueryParams) {
        List<SysDeptVo> list = iSysDeptService.getTree(sysOrgQueryParams);
        return new Result<List<SysDeptVo>>().ok(list);
    }

    @Log(module = "部门管理-新增", type = OperationTypeEnum.ADD, description = "新增部门信息")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:dept:save')")
    public Result<?> save(@RequestBody SysDeptAddParams sysOrgAddParams) {
        iSysDeptService.save(sysOrgAddParams);
        return new Result<>().ok();
    }

    @Log(module = "部门管理-修改", type = OperationTypeEnum.UPDATE, description = "修改部门信息")
    @PutMapping("update")
    @PreAuthorize("hasAuthority('sys:dept:update')")
    public Result<?> update(@Valid @RequestBody SysDeptAddParams sysOrgAddParams) {
        iSysDeptService.update(sysOrgAddParams);
        return new Result<>().ok();
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:dept:info')")
    public Result<SysDeptVo> get(@PathVariable("id") Long id) {
        SysDeptVo sysOrgVo = iSysDeptService.getById(id);
        return new Result<SysDeptVo>().ok(sysOrgVo);
    }

    @Log(module = "部门管理-删除", type = OperationTypeEnum.DELETE, description = "删除部门信息")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    public Result<?> delete(@PathVariable("id") Long id) {
        iSysDeptService.removeById(id);
        return new Result<>().ok();
    }

    @GetMapping("list")
    public Result<List<SysDeptVo>> list() {
        List<SysDeptVo> list = iSysDeptService.getList(null);
        return new Result<List<SysDeptVo>>().ok(list);
    }
}
