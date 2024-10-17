package com.zs.modules.sys.role.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.modules.sys.role.domain.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author 86740
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {


    List<SysRoleEntity> getList(Long userId);

    Set<Long> getDataScopeDeptIds(@Param("userId") Long userId);
}
