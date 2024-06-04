package com.zs.assets.allot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.assets.allot.domain.entity.AssetsAllotDetailsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 86740
 */
@Mapper
public interface AssetsAllotDetailsMapper extends BaseMapper<AssetsAllotDetailsEntity> {

    List<AssetsAllotDetailsEntity> getAllotDetails(Long allotId);
}
