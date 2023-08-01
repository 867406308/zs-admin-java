package com.zs.modules.sys.post.controller;

import com.zs.common.annotation.Log;
import com.zs.common.core.Result;
import com.zs.common.enums.OperationTypeEnum;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.post.domain.query.SysPostAddParams;
import com.zs.modules.sys.post.domain.query.SysPostQueryParams;
import com.zs.modules.sys.post.domain.vo.SysPostVo;
import com.zs.modules.sys.post.service.ISysPostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 86740
 */
@RestController
@RequestMapping("sys/post")
public class SysPostController {

    @Resource
    private ISysPostService iSysPostService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:post:page')")
    public Result page(SysPostQueryParams sysPostQueryParams){
        PageResult<SysPostVo> iPage =  iSysPostService.page(sysPostQueryParams);
        return new Result().ok(iPage);
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:post:list')")
    public Result list(){
        List<SysPostVo> list =  iSysPostService.getList();
        return new Result().ok(list);
    }

    @Log(module = "岗位管理-新增", type = OperationTypeEnum.ADD, description = "新增岗位信息")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:post:save')")
    public Result save(@RequestBody SysPostAddParams sysPostAddParams){

        iSysPostService.save(sysPostAddParams);
        return new Result().ok();
    }

    @Log(module = "岗位管理-修改", type = OperationTypeEnum.EDIT, description = "修改岗位信息")
    @PutMapping("update")
    @PreAuthorize("hasAuthority('sys:post:update')")
    public Result update(@RequestBody SysPostAddParams sysPostAddParams){
        iSysPostService.update(sysPostAddParams);
        return new Result().ok();
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:post:info')")
    public Result get(@PathVariable("id") Long id){
        SysPostVo sysOrgVo =  iSysPostService.getById(id);
        return new Result().ok(sysOrgVo);
    }

    @Log(module = "岗位管理-删除", type = OperationTypeEnum.DELETE, description = "删除岗位信息")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('sys:post:delete')")
    public Result delete(@PathVariable("id") Long id){
        iSysPostService.removeById(id);
        return new Result().ok();
    }
}
