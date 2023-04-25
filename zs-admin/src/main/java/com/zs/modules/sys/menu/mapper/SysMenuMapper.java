package com.zs.modules.sys.menu.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zs.modules.sys.menu.domain.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper  extends BaseMapper<SysMenuEntity> {

    List<SysMenuEntity> getList(Page<SysMenuEntity> page);


    List<SysMenuEntity> page(Page<SysMenuEntity> page, @Param("wrapper") QueryWrapper<SysMenuEntity> wrapper);
}
