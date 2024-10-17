package com.zs.modules.assets.classify.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.modules.assets.classify.domain.entity.AssetsClassifySchoolEntity;
import com.zs.modules.assets.classify.domain.query.AssetsClassifySchoolQueryParams;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 86740
 */
@Mapper
public interface AssetsClassifySchoolMapper extends BaseMapper<AssetsClassifySchoolEntity> {

    List<AssetsClassifySchoolEntity> getList(AssetsClassifySchoolQueryParams assetsClassifySchoolQueryParams);

    AssetsClassifySchoolEntity getById(Long id);
}
