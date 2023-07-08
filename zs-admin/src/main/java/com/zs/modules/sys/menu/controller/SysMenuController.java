package com.zs.modules.sys.menu.controller;


import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zs.common.annotation.Log;
import com.zs.common.core.Result;
import com.zs.common.enums.OperationTypeEnum;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.menu.domain.dto.SysMenuDTO;
import com.zs.modules.sys.menu.domain.entity.SysMenuEntity;
import com.zs.modules.sys.menu.domain.vo.SysMenuVo;
import com.zs.modules.sys.menu.service.ISysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/menu")
public class SysMenuController {


    @Resource
    private ISysMenuService iSysMenuService;

    @GetMapping("page")
    public Result page(Map<String, Object> params){
        PageResult<SysMenuVo> iPage =  iSysMenuService.page(params);
        return new Result().ok(iPage);
    }

    @GetMapping("list")
    public Result list(){
        List<SysMenuVo> list =  iSysMenuService.getList();
        return new Result().ok(list);
    }

    @Log(module = "菜单管理-新增", type = OperationTypeEnum.ADD, description = "新增菜单信息")
    @PostMapping("save")
    public Result save(@RequestBody SysMenuDTO sysMenuDTO){

        iSysMenuService.save(sysMenuDTO);
        return new Result().ok();
    }

    @Log(module = "菜单管理-修改", type = OperationTypeEnum.EDIT, description = "新增菜单信息")
    @PutMapping("update")
    public Result update(@RequestBody SysMenuDTO sysMenuDTO){
        iSysMenuService.update(sysMenuDTO);
        return new Result().ok();
    }


    @GetMapping("{id}")
    public Result get(@PathVariable("id") Long id){
        SysMenuVo sysMenuVo =  iSysMenuService.getById(id);
        return new Result().ok(sysMenuVo);
    }

}
