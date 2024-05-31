package com.zs.assets.classify.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.assets.classify.domain.entity.AssetsClassifySchoolEntity;
import com.zs.assets.classify.domain.query.AssetsClassifySchoolAddParams;
import com.zs.assets.classify.domain.query.AssetsClassifySchoolQueryParams;
import com.zs.assets.classify.domain.vo.AssetsClassifySchoolVo;

import java.util.List;

/**
 * @author 86740
 */
public interface IAssetsClassifySchoolService extends IService<AssetsClassifySchoolEntity> {

    List<AssetsClassifySchoolVo> getList(AssetsClassifySchoolQueryParams assetsClassifySchoolQueryParams);

    AssetsClassifySchoolVo get(Long id);

    void save(AssetsClassifySchoolAddParams assetsClassifySchoolAddParams);

    void update(AssetsClassifySchoolAddParams assetsClassifySchoolAddParams);

    void delete(Long id);
}
