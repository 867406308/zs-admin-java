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

import java.util.List;

/**
 * @author 86740
 */
public interface ISysUserService extends IService<SysUserEntity> {

    PageResult<SysUserVo> page(SysUserQueryParams sysUserQueryParams);

    void save(SysUserAddParams sysUserAddParams);

    void update(SysUserUpdateParams sysUserUpdateParams);

    /**
     * 重置密码
     **/
    void resetPassword(SysUserPasswordParams sysUserPasswordParams);

    /**
     * 获取详情
     **/
    SysUserInfoVo getById(Long id);

    List<SysUserVo> list(SysUserQueryParams sysUserQueryParams);




}
