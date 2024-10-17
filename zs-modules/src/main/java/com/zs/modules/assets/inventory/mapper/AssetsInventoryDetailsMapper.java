package com.zs.modules.assets.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zs.modules.assets.inventory.domain.entity.AssetsInventoryDetailsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author 86740
 */
@Mapper
public interface AssetsInventoryDetailsMapper extends BaseMapper<AssetsInventoryDetailsEntity> {

    IPage<AssetsInventoryDetailsEntity> page(Page<AssetsInventoryDetailsEntity> page, @Param("params") Map<String, Object> params);
}
