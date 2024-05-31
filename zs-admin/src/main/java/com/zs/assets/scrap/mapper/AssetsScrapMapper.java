package com.zs.assets.scrap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.assets.scrap.domain.entity.AssetsScrapEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 86740
 */
@Mapper
public interface AssetsScrapMapper extends BaseMapper<AssetsScrapEntity> {

    String getMaxSerialNo();
}
