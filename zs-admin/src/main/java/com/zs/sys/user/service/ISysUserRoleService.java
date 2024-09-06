package com.zs.sys.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.sys.user.domain.entity.SysUserRoleEntity;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysUserRoleService extends IService<SysUserRoleEntity> {

    /** 根据用户id和角色id列表保存用户角色关系 **/
    void saveOrUpdate(Long sysUserId, List<Long> sysRoleIdList);

    /** 根据用户id查询角色id列表 **/
    List<Long> queryRoleIdList(Long sysUserId);

    /** 根据用户id删除用户角色关系 **/
    void delByUserId(Long sysUserId);

    /** 根据角色id查询用户角色列表 **/
    List<Long> queryByRoleId(Long sysRoleId);
}
