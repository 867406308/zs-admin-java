package com.zs.sys.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.sys.user.domain.entity.SysUserRoleEntity;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysUserRoleService extends IService<SysUserRoleEntity> {

    void saveOrUpdate(Long sysUserId, List<Long> sysRoleIdList);

    List<Long> queryRoleIdList(Long sysUserId);
}
