package com.zs.modules.sys.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.modules.sys.role.domain.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysRoleMenuService extends IService<SysRoleMenuEntity> {


    /**
     * 保存角色菜单
     *
     * @param sysRoleId 角色ID
     * @param menuList  菜单列表
     */
    void save(Long sysRoleId, List<Long> menuList);


    /**
     * 根据角色获取对应的菜单ID列表集合
     *
     * @param sysRoleId 角色ID
     * @return 菜单ID列表集合
     */
    List<Long> getMenuList(Long sysRoleId);
}
