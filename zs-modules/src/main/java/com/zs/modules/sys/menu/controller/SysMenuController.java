package com.zs.modules.sys.menu.controller;


import com.zs.common.aop.annotation.Log;
import com.zs.common.core.core.Result;
import com.zs.common.core.enums.OperationTypeEnum;
import com.zs.common.core.page.PageResult;
import com.zs.modules.sys.menu.domain.params.SysMenuAddParams;
import com.zs.modules.sys.menu.domain.params.SysMenuQueryParams;
import com.zs.modules.sys.menu.domain.vo.SysMenuVo;
import com.zs.modules.sys.menu.service.ISysMenuService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 86740
 */
@RestController
@RequestMapping("system/sys/menu")
public class SysMenuController {


    @Resource
    private ISysMenuService iSysMenuService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:menu:page')")
    public Result<PageResult<SysMenuVo>> page(SysMenuQueryParams sysMenuQueryParams) {
        PageResult<SysMenuVo> iPage = iSysMenuService.page(sysMenuQueryParams);
        return new Result<PageResult<SysMenuVo>>().ok(iPage);
    }

    @GetMapping("nav")
    public Result<List<SysMenuVo>> nav() {
        List<SysMenuVo> list = iSysMenuService.getNavList();
        return new Result<List<SysMenuVo>>().ok(list);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<List<SysMenuVo>> list(SysMenuQueryParams sysMenuQueryParams) {
        List<SysMenuVo> list = iSysMenuService.getList(sysMenuQueryParams);
        return new Result<List<SysMenuVo>>().ok(list);
    }

    @Log(module = "菜单管理-新增", type = OperationTypeEnum.ADD, description = "新增菜单信息")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result<?> save(@RequestBody SysMenuAddParams sysMenuAddParams) {

        iSysMenuService.save(sysMenuAddParams);
        return new Result<>().ok();
    }

    @Log(module = "菜单管理-修改", type = OperationTypeEnum.UPDATE, description = "新增菜单信息")
    @PutMapping("update")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result<?> update(@RequestBody SysMenuAddParams sysMenuAddParams) {
        iSysMenuService.update(sysMenuAddParams);
        return new Result<>().ok();
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:menu:info')")
    public Result<SysMenuVo> get(@PathVariable("id") Long id) {
        SysMenuVo sysMenuVo = iSysMenuService.getById(id);
        return new Result<SysMenuVo>().ok(sysMenuVo);
    }

    @Log(module = "菜单管理-删除", type = OperationTypeEnum.DELETE, description = "删除菜单信息")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result<?> delete(@PathVariable("id") Long id) {
        iSysMenuService.removeById(id);
        return new Result<>().ok();
    }
}
