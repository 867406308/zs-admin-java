package com.zs.assets.classify.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.assets.classify.domain.entity.AssetsClassifyGbEntity;
import com.zs.assets.classify.domain.query.AssetsClassifyGbAddParams;
import com.zs.assets.classify.domain.query.AssetsClassifyGbQueryParams;
import com.zs.assets.classify.domain.vo.AssetsClassifyGbVo;

import java.util.List;

/**
 * @author 86740
 */
public interface IAssetsClassifyGbService extends IService<AssetsClassifyGbEntity> {

    List<AssetsClassifyGbVo> getList(AssetsClassifyGbQueryParams assetsClassifyGbQueryParams);

    AssetsClassifyGbVo get(Long id);

    void save(AssetsClassifyGbAddParams assetsClassifyGbAddParams);

    void update(AssetsClassifyGbAddParams assetsClassifyGbAddParams);

    void delete(Long id);
}
