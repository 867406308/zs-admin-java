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

    /**
     * @description 获取前端菜单导航
     * @author 86740
     * @date 9:29 2023-07-19
     * @return java.util.List<com.zs.modules.sys.menu.domain.vo.SysMenuVo>
     **/

    List<SysMenuVo> getNavList();

    /**
     * @description 获取菜单列表
     * @author 86740
     * @date 9:29 2023-07-19
     * @return java.util.List<com.zs.modules.sys.menu.domain.vo.SysMenuVo>
     **/

    List<SysMenuVo> getList();

    void save(SysMenuAddParams sysMenuAddParams);

    void update(SysMenuAddParams sysMenuAddParams);

    SysMenuVo getById(Long id);
}
