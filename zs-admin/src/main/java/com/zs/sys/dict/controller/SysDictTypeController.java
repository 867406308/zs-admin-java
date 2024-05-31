package com.zs.sys.dict.controller;

import com.zs.common.aop.annotation.Log;
import com.zs.common.core.core.Result;
import com.zs.common.core.enums.OperationTypeEnum;
import com.zs.common.core.page.PageResult;
import com.zs.sys.dict.domain.params.SysDictTypeAddParams;
import com.zs.sys.dict.domain.params.SysDictTypeQueryParams;
import com.zs.sys.dict.domain.vo.SysDictTypeVo;
import com.zs.sys.dict.service.ISysDictTypeService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 86740
 */
@RestController
@RequestMapping("system/sys/dictType")
public class SysDictTypeController {

    @Resource
    private ISysDictTypeService sysDictTypeService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<SysDictTypeVo>> page(SysDictTypeQueryParams sysDictTypeQueryParams) {
        PageResult<SysDictTypeVo> iPage = sysDictTypeService.page(sysDictTypeQueryParams);
        return new Result<PageResult<SysDictTypeVo>>().ok(iPage);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:dict:list')")
    public Result<List<SysDictTypeVo>> list(SysDictTypeQueryParams sysDictTypeQueryParams) {
        return new Result<List<SysDictTypeVo>>().ok(sysDictTypeService.list(sysDictTypeQueryParams));
    }

    @Log(module = "字典类型-新增", type = OperationTypeEnum.ADD, description = "新增字典类型")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:dict:save')")
    public Result<?> save(@RequestBody SysDictTypeAddParams sysDictTypeAddParams) {

        sysDictTypeService.save(sysDictTypeAddParams);
        return new Result<>().ok();
    }

    @Log(module = "字典类型-修改", type = OperationTypeEnum.UPDATE, description = "修改字典类型")
    @PutMapping("update")
    @PreAuthorize("hasAuthority('sys:dict:update')")
    public Result<?> update(@RequestBody SysDictTypeAddParams sysDictTypeAddParams) {
        sysDictTypeService.update(sysDictTypeAddParams);
        return new Result<>().ok();
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:dict:info')")
    public Result<SysDictTypeVo> get(@PathVariable("id") Long id) {
        SysDictTypeVo sysDictTypeVo = sysDictTypeService.getById(id);
        return new Result<SysDictTypeVo>().ok(sysDictTypeVo);
    }


    @Log(module = "字典类型-删除", type = OperationTypeEnum.DELETE, description = "删除字典类型")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    public Result<?> delete(@PathVariable("id") Long id) {
        sysDictTypeService.deleteById(id);
        return new Result<>().ok();
    }

}
