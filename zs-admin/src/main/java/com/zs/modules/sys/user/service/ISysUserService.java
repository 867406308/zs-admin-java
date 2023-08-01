package com.zs.modules.sys.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.user.domain.dto.SysUserDTO;
import com.zs.modules.sys.user.domain.query.SysUserAddParams;
import com.zs.modules.sys.user.domain.entity.SysUserEntity;
import com.zs.modules.sys.user.domain.query.SysUserQueryParams;
import com.zs.modules.sys.user.domain.vo.SysUserVo;

import java.util.Set;

public interface ISysUserService extends IService<SysUserEntity> {

    PageResult<SysUserVo> page(SysUserQueryParams sysUserQueryParams);

    void save(SysUserAddParams sysUserAddParams);

    void update(SysUserAddParams sysUserAddParams);

    SysUserVo getById(Long id);
    /**
     * 通过用户名查询用户信息
     * @param userName 用户名
     * @return 用户对象
     */
    SysUserDTO selectByUserName(String userName);


    Set<String> getPermissions(SysUserDTO sysUserDTO);



}
