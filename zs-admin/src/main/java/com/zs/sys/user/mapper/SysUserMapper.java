package com.zs.sys.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zs.common.mp.annotation.DataScope;
import com.zs.sys.user.domain.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 86740
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    @DataScope
    IPage<SysUserEntity> page(Page<SysUserEntity> page, @Param("params") Map<String, Object> params);

    void updateDeleted(Long sysUserId);

    List<SysUserEntity> getList(@Param("params") Map<String, Object> params);

    SysUserEntity selectByUserName(String userName);

}
