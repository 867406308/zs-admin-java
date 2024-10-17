package com.zs.modules.assets.allot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.modules.assets.allot.domain.entity.AssetsAllotEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 86740
 */
@Mapper
public interface AssetsAllotMapper extends BaseMapper<AssetsAllotEntity> {


    /**
     * 根据当前日期查询最大序列号的
     * @param nowDateStr 当前日期(格式yyyyMMdd)
     */
    String getMaxSerialNo(@Param("nowDateStr") String nowDateStr);
}
