package com.zs.modules.sys.post.controller;

import com.zs.common.annotation.Log;
import com.zs.common.core.Result;
import com.zs.common.enums.OperationTypeEnum;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.post.domain.query.SysPostAddParams;
import com.zs.modules.sys.post.domain.query.SysPostQueryParams;
import com.zs.modules.sys.post.domain.vo.SysPostVo;
import com.zs.modules.sys.post.service.ISysPostService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/post")
public class SysPostController {

    @Resource
    private ISysPostService iSysPostService;

    @GetMapping("page")
    public Result page(SysPostQueryParams sysPostQueryParams){
        PageResult<SysPostVo> iPage =  iSysPostService.page(sysPostQueryParams);
        return new Result().ok(iPage);
    }

    @GetMapping("list")
    public Result list(){
        List<SysPostVo> list =  iSysPostService.getList();
        return new Result().ok(list);
    }

    @Log(module = "岗位管理-新增", type = OperationTypeEnum.ADD, description = "新增岗位信息")
    @PostMapping("save")
    public Result save(@RequestBody SysPostAddParams sysPostAddParams){

        iSysPostService.save(sysPostAddParams);
        return new Result().ok();
    }

    @Log(module = "岗位管理-修改", type = OperationTypeEnum.EDIT, description = "修改岗位信息")
    @PutMapping("update")
    public Result update(@RequestBody SysPostAddParams sysPostAddParams){
        iSysPostService.update(sysPostAddParams);
        return new Result().ok();
    }


    @GetMapping("{id}")
    public Result get(@PathVariable("id") Long id){
        SysPostVo sysOrgVo =  iSysPostService.getById(id);
        return new Result().ok(sysOrgVo);
    }
}
