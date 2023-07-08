package com.zs.modules.sys.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.modules.sys.user.domain.dto.SysUserDTO;
import com.zs.modules.sys.user.domain.entity.SysUserEntity;

public interface ISysUserService extends IService<SysUserEntity> {


    public void save(SysUserDTO sysUserDTO);
    /**
     * 通过用户名查询用户信息
     * @param userName 用户名
     * @return 用户对象
     */
    
    public SysUserDTO selectByUserName(String userName);



}
