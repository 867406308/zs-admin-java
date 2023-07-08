package com.zs.modules.sys.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.role.domain.entity.SysRoleEntity;
import com.zs.modules.sys.role.domain.query.SysRoleAddParams;
import com.zs.modules.sys.role.domain.query.SysRoleQueryParams;
import com.zs.modules.sys.role.domain.vo.SysRoleVo;

import java.util.List;
import java.util.Map;

/**
 * The interface Sys role service.
 */
public interface ISysRoleService extends IService<SysRoleEntity> {

    PageResult<SysRoleVo> page(SysRoleQueryParams sysRoleQueryParams);

    List<SysRoleVo> getList();


    void save(SysRoleAddParams sysRoleAddParams);


    void update(SysRoleAddParams sysRoleAddParams);


    SysRoleVo getById(Long id);
}
