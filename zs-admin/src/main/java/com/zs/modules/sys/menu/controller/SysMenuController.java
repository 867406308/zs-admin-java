package com.zs.modules.sys.menu.controller;

import com.zs.common.annotation.Log;
import com.zs.common.core.Result;
import com.zs.common.enums.OperationTypeEnum;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.menu.domain.params.SysMenuAddParams;
import com.zs.modules.sys.menu.domain.params.SysMenuQueryParams;
import com.zs.modules.sys.menu.domain.vo.SysMenuVo;
import com.zs.modules.sys.menu.service.ISysMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 86740
 */
@RestController
@RequestMapping("sys/menu")
public class SysMenuController {


    @Resource
    private ISysMenuService iSysMenuService;

    @GetMapping("page")
    public Result page(SysMenuQueryParams sysMenuQueryParams){
        PageResult<SysMenuVo> iPage =  iSysMenuService.page(sysMenuQueryParams);
        return new Result().ok(iPage);
    }

    @GetMapping("list")
    public Result list(){
        List<SysMenuVo> list =  iSysMenuService.getList();
        return new Result().ok(list);
    }

    @Log(module = "菜单管理-新增", type = OperationTypeEnum.ADD, description = "新增菜单信息")
    @PostMapping("save")
    public Result save(@RequestBody SysMenuAddParams sysMenuAddParams){

        iSysMenuService.save(sysMenuAddParams);
        return new Result().ok();
    }

    @Log(module = "菜单管理-修改", type = OperationTypeEnum.EDIT, description = "新增菜单信息")
    @PutMapping("update")
    public Result update(@RequestBody SysMenuAddParams sysMenuAddParams){
        iSysMenuService.update(sysMenuAddParams);
        return new Result().ok();
    }


    @GetMapping("{id}")
    public Result get(@PathVariable("id") Long id){
        SysMenuVo sysMenuVo =  iSysMenuService.getById(id);
        return new Result().ok(sysMenuVo);
    }

}
