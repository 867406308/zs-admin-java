package com.zs.modules.sys.role.controller;

import com.zs.common.annotation.Log;
import com.zs.common.core.Result;
import com.zs.common.enums.OperationTypeEnum;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.role.domain.query.SysRoleAddParams;
import com.zs.modules.sys.role.domain.query.SysRoleQueryParams;
import com.zs.modules.sys.role.domain.vo.SysRoleVo;
import com.zs.modules.sys.role.service.ISysRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 86740
 */
@RestController
@RequestMapping("sys/role")
public class SysRoleController {

    @Resource
    private ISysRoleService iSysRoleService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:role:page')")
    public Result page(SysRoleQueryParams sysRoleQueryParams){
        PageResult<SysRoleVo> iPage =  iSysRoleService.page(sysRoleQueryParams);
        return new Result().ok(iPage);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public Result list(){
        List<SysRoleVo> list =  iSysRoleService.getList();
        return new Result().ok(list);
    }

    @Log(module = "角色管理-新增", type = OperationTypeEnum.ADD, description = "新增角色信息")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:role:save')")
    public Result save(@RequestBody SysRoleAddParams sysPostAddParams){

        iSysRoleService.save(sysPostAddParams);
        return new Result().ok();
    }

    @Log(module = "角色管理-修改", type = OperationTypeEnum.EDIT, description = "修改角色信息")
    @PutMapping("update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result update(@RequestBody SysRoleAddParams sysPostAddParams){
        iSysRoleService.update(sysPostAddParams);
        return new Result().ok();
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:role:info')")
    public Result get(@PathVariable("id") Long id){
        SysRoleVo sysRoleVo =  iSysRoleService.getById(id);
        return new Result().ok(sysRoleVo);
    }


    @Log(module = "角色管理-删除", type = OperationTypeEnum.DELETE, description = "删除角色信息")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public Result delete(@PathVariable("id") Long id){
        iSysRoleService.deleteById(id);
        return new Result().ok();
    }
}
