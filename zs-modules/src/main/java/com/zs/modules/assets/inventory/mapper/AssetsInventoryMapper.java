package com.zs.modules.assets.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zs.modules.assets.inventory.domain.entity.AssetsInventoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author 86740
 */
@Mapper
public interface AssetsInventoryMapper extends BaseMapper<AssetsInventoryEntity> {

    IPage<AssetsInventoryEntity> page(Page<AssetsInventoryEntity> page, @Param("params") Map<String, Object> params);

    AssetsInventoryEntity get(Long id);
}
