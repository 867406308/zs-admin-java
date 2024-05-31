package com.zs.sys.role.controller;


import com.zs.common.aop.annotation.Log;
import com.zs.common.core.core.Result;
import com.zs.common.core.enums.OperationTypeEnum;
import com.zs.common.core.page.PageResult;
import com.zs.sys.role.domain.params.SysRoleAddParams;
import com.zs.sys.role.domain.params.SysRoleQueryParams;
import com.zs.sys.role.domain.vo.SysRoleVo;
import com.zs.sys.role.service.ISysRoleService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 86740
 */
@RestController
@RequestMapping("system/sys/role")
public class SysRoleController {

    @Resource
    private ISysRoleService iSysRoleService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:role:page')")
    public Result<PageResult<SysRoleVo>> page(SysRoleQueryParams sysRoleQueryParams) {
        PageResult<SysRoleVo> iPage = iSysRoleService.page(sysRoleQueryParams);
        return new Result<PageResult<SysRoleVo>>().ok(iPage);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public Result<List<SysRoleVo>> list() {
        List<SysRoleVo> list = iSysRoleService.getList();
        return new Result<List<SysRoleVo>>().ok(list);
    }

    @Log(module = "角色管理-新增", type = OperationTypeEnum.ADD, description = "新增角色信息")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:role:save')")
    public Result<?> save(@RequestBody SysRoleAddParams sysPostAddParams) {

        iSysRoleService.save(sysPostAddParams);
        return new Result<>().ok();
    }

    @Log(module = "角色管理-修改", type = OperationTypeEnum.UPDATE, description = "修改角色信息")
    @PutMapping("update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<?> update(@RequestBody SysRoleAddParams sysPostAddParams) {
        iSysRoleService.update(sysPostAddParams);
        return new Result<>().ok();
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:role:info')")
    public Result<SysRoleVo> get(@PathVariable("id") Long id) {
        SysRoleVo sysRoleVo = iSysRoleService.getById(id);
        return new Result<SysRoleVo>().ok(sysRoleVo);
    }


    @Log(module = "角色管理-删除", type = OperationTypeEnum.DELETE, description = "删除角色信息")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public Result<?> delete(@PathVariable("id") Long id) {
        iSysRoleService.deleteById(id);
        return new Result<>().ok();
    }
}
