package com.zs.modules.sys.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zs.modules.sys.menu.domain.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface SysMenuMapper  extends BaseMapper<SysMenuEntity> {

    List<SysMenuEntity> getList(Page<SysMenuEntity> page);

    Set<String> getPermissions(Long sysUserId);

}
