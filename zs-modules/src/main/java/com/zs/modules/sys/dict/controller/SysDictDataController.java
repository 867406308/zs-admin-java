package com.zs.modules.sys.dict.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zs.common.aop.annotation.Log;
import com.zs.common.core.core.Result;
import com.zs.common.core.enums.OperationTypeEnum;
import com.zs.common.core.excel.ExcelUtils;
import com.zs.common.core.page.PageResult;
import com.zs.modules.sys.dict.domain.excel.SysDictDataExcel;
import com.zs.modules.sys.dict.domain.params.SysDictDataAddParams;
import com.zs.modules.sys.dict.domain.params.SysDictDataQueryParams;
import com.zs.modules.sys.dict.domain.vo.SysDictDataVo;
import com.zs.modules.sys.dict.service.ISysDictDataService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author 86740
 */
@RestController
@RequestMapping("system/sys/dictData")
public class SysDictDataController {

    @Resource
    private ISysDictDataService sysDictDataService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:dict:page')")
    public Result<PageResult<SysDictDataVo>> page(SysDictDataQueryParams sysDictDataQueryParams) {

        PageResult<SysDictDataVo> iPage = sysDictDataService.page(sysDictDataQueryParams);
        return new Result<PageResult<SysDictDataVo>>().ok(iPage);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:dict:list')")
    public Result<List<SysDictDataVo>> list(SysDictDataQueryParams sysDictDataQueryParams) {
        List<SysDictDataVo> list = sysDictDataService.list(sysDictDataQueryParams);

        return new Result<List<SysDictDataVo>>().ok(list);
    }

    @PostMapping("save")
    @Log(module = "字典数据-新增", type = OperationTypeEnum.ADD, description = "新增字典数据")
    @PreAuthorize("hasAuthority('sys:dict:save')")
    public Result<?> save(@RequestBody SysDictDataAddParams sysDictDataAddParams) {
        sysDictDataService.save(sysDictDataAddParams);
        return new Result<>().ok();
    }

    @PutMapping("update")
    @Log(module = "字典数据-修改", type = OperationTypeEnum.UPDATE, description = "修改字典数据")
    @PreAuthorize("hasAuthority('sys:dict:update')")
    public Result<?> update(@RequestBody SysDictDataAddParams sysDictDataAddParams) {
        sysDictDataService.update(sysDictDataAddParams);
        return new Result<>().ok();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:dict:info')")
    public Result<SysDictDataVo> get(@PathVariable("id") Long id) {
        return new Result<SysDictDataVo>().ok(sysDictDataService.getById(id));
    }

    @Log(module = "字典数据-删除", type = OperationTypeEnum.DELETE, description = "删除字典数据")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    public Result<?> delete(@PathVariable("id") Long id) {
        sysDictDataService.deleteById(id);
        return new Result<>().ok();
    }

    @Log(module = "字典数据-批量删除", type = OperationTypeEnum.DELETE_BATCH, description = "批量删除字典数据")
    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:dict:batchDelete')")
    public Result<?> batchDelete(@RequestBody Long[] ids) {
        sysDictDataService.batchDelById(ids);
        return new Result<>().ok();
    }
    @Log(module = "字典数据-导出", type = OperationTypeEnum.EXPORT, description = "导出字典数据信息")
    @GetMapping("export")
    @PreAuthorize("hasAuthority('sys:post:export')")
    public void export(HttpServletResponse response, SysDictDataQueryParams sysDictDataQueryParams) throws IOException {
        List<SysDictDataVo> list = sysDictDataService.list(sysDictDataQueryParams);
        List<SysDictDataExcel> excelList = BeanUtil.copyToList(list, SysDictDataExcel.class);
        ExcelUtils.exportExcel(response, "字典信息.xlsx", SysDictDataExcel.class, excelList);

    }
}
