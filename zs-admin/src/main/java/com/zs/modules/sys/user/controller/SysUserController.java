package com.zs.modules.sys.user.controller;

import com.zs.common.annotation.Log;
import com.zs.common.core.Result;
import com.zs.common.enums.OperationTypeEnum;
import com.zs.common.model.LoginUserInfo;
import com.zs.common.page.PageResult;
import com.zs.framework.security.utils.SecurityUtil;
import com.zs.modules.sys.post.domain.query.SysPostAddParams;
import com.zs.modules.sys.post.domain.vo.SysPostVo;
import com.zs.modules.sys.user.domain.query.SysUserAddParams;
import com.zs.modules.sys.user.domain.query.SysUserQueryParams;
import com.zs.modules.sys.user.domain.vo.SysUserVo;
import com.zs.modules.sys.user.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 86740
 */
@RestController
@RequestMapping("sys/user")
public class SysUserController {


    @Resource
    private ISysUserService iSysUserService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result page(SysUserQueryParams sysUserQueryParams){
        PageResult<SysUserVo> iPage =  iSysUserService.page(sysUserQueryParams);
        return new Result().ok(iPage);
    }

    @Log(module = "用户管理-新增", type = OperationTypeEnum.ADD, description = "新增用户信息")
    @PostMapping("save")
    public void save(@RequestBody SysUserAddParams sysUserAddParams){

         iSysUserService.save(sysUserAddParams);
    }

    @Log(module = "用户管理-修改", type = OperationTypeEnum.EDIT, description = "修改用户信息")
    @PutMapping("update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result update(@RequestBody SysUserAddParams sysUserAddParams){
        iSysUserService.update(sysUserAddParams);
        return new Result().ok();
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(){
        LoginUserInfo loginUserInfo = SecurityUtil.getUserInfo();
        return new Result().ok(loginUserInfo);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public Result get(@PathVariable("id") Long id){
        SysUserVo sysUserVo =  iSysUserService.getById(id);
        return new Result().ok(sysUserVo);
    }
    @Log(module = "用户管理-删除", type = OperationTypeEnum.DELETE, description = "删除用户信息")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result delete(@PathVariable("id") Long id){
        iSysUserService.removeById(id);
        return new Result().ok();
    }
}
