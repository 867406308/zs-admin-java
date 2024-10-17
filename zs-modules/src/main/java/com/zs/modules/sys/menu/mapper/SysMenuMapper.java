package com.zs.modules.sys.menu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zs.modules.sys.menu.domain.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 86740
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    List<SysMenuEntity> getList(Page<SysMenuEntity> page);

    List<SysMenuEntity> getMenuList(Long sysUserId);

    List<SysMenuEntity> getPermissions(Long sysUserId);

}
