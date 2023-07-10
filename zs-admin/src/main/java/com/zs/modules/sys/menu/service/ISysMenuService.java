package com.zs.modules.sys.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.menu.domain.params.SysMenuAddParams;
import com.zs.modules.sys.menu.domain.entity.SysMenuEntity;
import com.zs.modules.sys.menu.domain.params.SysMenuQueryParams;
import com.zs.modules.sys.menu.domain.vo.SysMenuVo;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysMenuService extends IService<SysMenuEntity> {


    PageResult<SysMenuVo> page(SysMenuQueryParams sysMenuQueryParams);

    List<SysMenuVo> getList();

    void save(SysMenuAddParams sysMenuAddParams);

    void update(SysMenuAddParams sysMenuAddParams);

    SysMenuVo getById(Long id);
}
