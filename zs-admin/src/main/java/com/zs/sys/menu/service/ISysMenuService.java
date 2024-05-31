package com.zs.sys.menu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.sys.menu.domain.entity.SysMenuEntity;
import com.zs.sys.menu.domain.params.SysMenuAddParams;
import com.zs.sys.menu.domain.params.SysMenuQueryParams;
import com.zs.sys.menu.domain.vo.SysMenuVo;

import java.util.List;
import java.util.Set;

/**
 * @author 86740
 */
public interface ISysMenuService extends IService<SysMenuEntity> {


    PageResult<SysMenuVo> page(SysMenuQueryParams sysMenuQueryParams);

    List<SysMenuVo> getNavList();

    List<SysMenuVo> getList(SysMenuQueryParams sysMenuQueryParams);

    void save(SysMenuAddParams sysMenuAddParams);

    void update(SysMenuAddParams sysMenuAddParams);

    SysMenuVo getById(Long id);

    Set<String> getAllPermissions();

    Set<String> getPermissions(Long sysUserId);
}
