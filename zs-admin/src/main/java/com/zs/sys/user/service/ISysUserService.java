package com.zs.sys.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.sys.user.domain.entity.SysUserEntity;
import com.zs.sys.user.domain.params.SysUserAddParams;
import com.zs.sys.user.domain.params.SysUserPasswordParams;
import com.zs.sys.user.domain.params.SysUserQueryParams;
import com.zs.sys.user.domain.params.SysUserUpdateParams;
import com.zs.sys.user.domain.vo.SysUserInfoVo;
import com.zs.sys.user.domain.vo.SysUserVo;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysUserService extends IService<SysUserEntity> {

    /** 分页 **/
    PageResult<SysUserVo> page(SysUserQueryParams sysUserQueryParams);

    /** 新增 **/
    void save(SysUserAddParams sysUserAddParams);

    /** 修改 **/
    void update(SysUserUpdateParams sysUserUpdateParams);

    /** 批量删除 **/
    void batchDelById(Long[] ids);

    /** 删除 **/
    void delById(Long id);

    /** 重置密码 **/
    void resetPassword(SysUserPasswordParams sysUserPasswordParams);

    /** 获取详情 **/
    SysUserInfoVo getById(Long id);

    /** 列表 **/
    @Nullable
    List<SysUserVo> list(SysUserQueryParams sysUserQueryParams);



}
