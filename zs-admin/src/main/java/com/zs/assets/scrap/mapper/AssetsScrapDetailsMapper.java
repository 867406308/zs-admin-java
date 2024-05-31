package com.zs.assets.scrap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zs.assets.scrap.domain.entity.AssetsScrapDetailsEntity;
import com.zs.assets.scrap.domain.params.AssetsScrapDetailsQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 86740
 */
@Mapper
public interface AssetsScrapDetailsMapper extends BaseMapper<AssetsScrapDetailsEntity> {

    IPage<AssetsScrapDetailsEntity> page(Page<AssetsScrapDetailsEntity> page, @Param("params") Map<String, Object> params);

    List<AssetsScrapDetailsEntity> list(AssetsScrapDetailsQueryParams assetsScrapDetailsQueryParams);
}
