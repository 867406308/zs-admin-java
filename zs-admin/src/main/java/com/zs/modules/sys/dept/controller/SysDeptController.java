package com.zs.modules.sys.dept.controller;

import com.zs.common.annotation.Log;
import com.zs.common.core.Result;
import com.zs.common.enums.OperationTypeEnum;
import com.zs.modules.sys.dept.domain.query.SysDeptAddParams;
import com.zs.modules.sys.dept.domain.vo.SysDeptVo;
import com.zs.modules.sys.dept.service.ISysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 86740
 */
@RestController
@RequestMapping("sys/dept")
public class SysDeptController {

    @Resource
    private ISysDeptService iSysDeptService;


    @GetMapping("tree")
    @PreAuthorize("hasAuthority('sys:dept:list')")
    public Result list(){
        List<SysDeptVo> list =  iSysDeptService.getList();
        return new Result().ok(list);
    }

    @Log(module = "部门管理-新增", type = OperationTypeEnum.ADD, description = "新增部门信息")
    @PostMapping("save")
    public Result save(@RequestBody SysDeptAddParams sysOrgAddParams){
        iSysDeptService.save(sysOrgAddParams);
        return new Result().ok();
    }

    @Log(module = "部门管理-修改", type = OperationTypeEnum.EDIT, description = "修改部门信息")
    @PutMapping("update")
    public Result update(@Valid @RequestBody SysDeptAddParams sysOrgAddParams){
        iSysDeptService.update(sysOrgAddParams);
        return new Result().ok();
    }


    @GetMapping("{id}")
    public Result get(@PathVariable("id") Long id){
        SysDeptVo sysOrgVo =  iSysDeptService.getById(id);
        return new Result().ok(sysOrgVo);
    }

    @Log(module = "部门管理-删除", type = OperationTypeEnum.DELETE, description = "删除部门信息")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") Long id){
        iSysDeptService.removeById(id);
        return new Result().ok();
    }
}
