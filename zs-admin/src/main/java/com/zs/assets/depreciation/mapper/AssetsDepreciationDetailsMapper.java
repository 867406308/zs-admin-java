package com.zs.assets.depreciation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zs.assets.depreciation.domain.entity.AssetsDepreciationDetailsEntity;
import com.zs.assets.info.domain.entity.AssetsInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 资产折旧明细mapper
 *
 * @author 86740
 */
@Mapper
public interface AssetsDepreciationDetailsMapper extends BaseMapper<AssetsDepreciationDetailsEntity> {

    IPage<AssetsDepreciationDetailsEntity> page(Page<AssetsDepreciationDetailsEntity> page, @Param("params") Map<String, Object> params);
}
