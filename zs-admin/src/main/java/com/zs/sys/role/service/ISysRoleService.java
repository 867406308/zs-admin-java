package com.zs.sys.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.sys.role.domain.entity.SysRoleEntity;
import com.zs.sys.role.domain.params.SysRoleAddParams;
import com.zs.sys.role.domain.params.SysRoleQueryParams;
import com.zs.sys.role.domain.vo.SysRoleVo;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysRoleService extends IService<SysRoleEntity> {

    PageResult<SysRoleVo> page(SysRoleQueryParams sysRoleQueryParams);

    List<SysRoleVo> getList();


    void save(SysRoleAddParams sysRoleAddParams);


    void update(SysRoleAddParams sysRoleAddParams);


    SysRoleVo getById(Long id);

    void deleteById(Long id);
}
