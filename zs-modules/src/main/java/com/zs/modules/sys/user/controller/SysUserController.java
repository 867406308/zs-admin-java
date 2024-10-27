package com.zs.modules.sys.user.controller;


import cn.hutool.core.bean.BeanUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zs.common.aop.annotation.Log;
import com.zs.common.core.core.Result;
import com.zs.common.core.crypto.annotation.Decryption;
import com.zs.common.core.crypto.annotation.Encryption;
import com.zs.common.core.enums.OperationTypeEnum;
import com.zs.common.core.excel.ExcelUtils;
import com.zs.common.core.model.LoginUserInfo;
import com.zs.common.core.page.PageResult;
import com.zs.common.core.utils.SecurityUtil;
import com.zs.modules.sys.user.domain.excel.SysUserExcel;
import com.zs.modules.sys.user.domain.params.SysUserAddParams;
import com.zs.modules.sys.user.domain.params.SysUserPasswordParams;
import com.zs.modules.sys.user.domain.params.SysUserQueryParams;
import com.zs.modules.sys.user.domain.params.SysUserUpdateParams;
import com.zs.modules.sys.user.domain.vo.SysUserInfoVo;
import com.zs.modules.sys.user.domain.vo.SysUserVo;
import com.zs.modules.sys.user.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("system/sys/user")
@Tag(name = "用户管理")
public class SysUserController {


    @Resource
    private ISysUserService iSysUserService;


    @Encryption
    @ApiOperationSupport(author = "zs")
    @Operation(summary = "分页获取用户信息")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<PageResult<SysUserVo>> page(SysUserQueryParams sysUserQueryParams) {
        PageResult<SysUserVo> iPage = iSysUserService.page(sysUserQueryParams);
        return new Result<PageResult<SysUserVo>>().ok(iPage);
    }


    @ApiOperationSupport(author = "zs")
    @Operation(summary = "新增用户信息")
    @Log(module = "用户管理-新增", type = OperationTypeEnum.ADD, description = "新增用户信息")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public Result<?> save(@RequestBody SysUserAddParams sysUserAddParams) {
        iSysUserService.save(sysUserAddParams);
        return new Result<>().ok();
    }


    @Decryption
    @ApiOperationSupport(author = "zs")
    @Operation(summary = "修改用户信息")
    @Log(module = "用户管理-修改", type = OperationTypeEnum.UPDATE, description = "修改用户信息")
    @PutMapping("update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result<?> update(@RequestBody SysUserUpdateParams sysUserUpdateParams) {
        iSysUserService.update(sysUserUpdateParams);
        return new Result<>().ok();
    }

    @Log(module = "用户管理-密码修改", type = OperationTypeEnum.UPDATE, description = "修改用户密码信息")
    @PutMapping("resetPassword")
    @PreAuthorize("hasAuthority('sys:user:resetpassword')")
    public Result<?> resetPassword(@RequestBody SysUserPasswordParams sysUserPasswordParams) {
        iSysUserService.resetPassword(sysUserPasswordParams);
        return new Result<>().ok();
    }


    @GetMapping("getUserInfo")
    public Result<LoginUserInfo> getUserInfo() {
        LoginUserInfo loginUserInfo = SecurityUtil.getUserInfo();
        return new Result<LoginUserInfo>().ok(loginUserInfo);
    }


    @Encryption
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public Result<SysUserInfoVo> get(@PathVariable("id") Long id) {
        SysUserInfoVo sysUserVo = iSysUserService.getById(id);
        return new Result<SysUserInfoVo>().ok(sysUserVo);
    }

    @Log(module = "用户管理-删除", type = OperationTypeEnum.DELETE, description = "删除用户信息")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result<?> delete(@PathVariable("id") Long id) {
        iSysUserService.delById(id);
        return new Result<>().ok();
    }

    @Log(module = "用户管理-批量删除", type = OperationTypeEnum.DELETE_BATCH, description = "批量删除用户信息")
    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:user:batchDelete')")
    public Result<?> batchDelete(@RequestBody Long[] ids) {
        iSysUserService.batchDelById(ids);
        return new Result<>().ok();
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result<List<SysUserVo>> list(SysUserQueryParams sysUserQueryParams) {
        List<SysUserVo> sysUserVos = BeanUtil.copyToList(iSysUserService.list(sysUserQueryParams), SysUserVo.class);
        return new Result<List<SysUserVo>>().ok(sysUserVos);
    }

    @PostMapping("getUserListByIds")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result<List<SysUserVo>> getUserList(@RequestBody Long[] sysUserIds) {
        List<SysUserVo> sysUserVos = BeanUtil.copyToList(iSysUserService.getUserList(sysUserIds), SysUserVo.class);
        return new Result<List<SysUserVo>>().ok(sysUserVos);
    }

    @Log(module = "用户管理-导出", type = OperationTypeEnum.EXPORT, description = "导出用户信息")
    @GetMapping("export")
    @PreAuthorize("hasAuthority('sys:user:export')")
    public void export(HttpServletResponse response, SysUserQueryParams sysUserQueryParams) throws IOException {
        List<SysUserVo> list = iSysUserService.list(sysUserQueryParams);
        List<SysUserExcel> excelList = BeanUtil.copyToList(list, SysUserExcel.class);
        ExcelUtils.exportExcel(response, "用户信息.xlsx", SysUserExcel.class, excelList);

    }
}
