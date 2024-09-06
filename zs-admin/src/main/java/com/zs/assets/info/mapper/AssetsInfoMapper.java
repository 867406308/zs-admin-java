package com.zs.assets.info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zs.assets.info.domain.entity.AssetsInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 86740
 */
@Mapper
public interface AssetsInfoMapper extends BaseMapper<AssetsInfoEntity> {

    IPage<AssetsInfoEntity> page(Page<AssetsInfoEntity> page, @Param("params") Map<String, Object> params);

    List<AssetsInfoEntity> list(@Param("params") Map<String, Object> params);

    List<AssetsInfoEntity> getBySerialNoList(@Param("serialNoList") List<String> serialNoList);

    String getMaxInNo();

    AssetsInfoEntity getById(Long id);

    void updateUseStatusCodeBySerialNo(AssetsInfoEntity assetsInfoEntity);

    /**
     * 获取总资产总金额
     */
    BigDecimal getTotalPrice(@Param("params") Map<String, Object> params);

}




