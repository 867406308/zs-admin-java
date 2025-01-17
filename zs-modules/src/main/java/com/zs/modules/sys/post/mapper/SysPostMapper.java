package com.zs.modules.sys.post.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zs.modules.sys.post.domain.entity.SysPostEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 86740
 */
@Mapper
public interface SysPostMapper extends BaseMapper<SysPostEntity> {

    IPage<SysPostEntity> page(Page<SysPostEntity> page, @Param("params") Map<String, Object> params);

    List<SysPostEntity> getList(@Param("params") Map<String, Object> params);
}
