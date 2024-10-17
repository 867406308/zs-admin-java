package com.zs.modules.assets.scrap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.modules.assets.scrap.domain.entity.AssetsScrapEntity;
import com.zs.modules.assets.scrap.domain.params.AssetsScrapAddParams;
import com.zs.modules.assets.scrap.domain.params.AssetsScrapQueryParams;
import com.zs.modules.assets.scrap.domain.vo.AssetsScrapVo;
import com.zs.common.core.page.PageResult;

/**
 * @author 86740
 */
public interface AssetsScrapService extends IService<AssetsScrapEntity> {


    PageResult<AssetsScrapVo> page(AssetsScrapQueryParams assetsScrapQueryParams);

    /**
     * 一键折旧
     **/
    void save(AssetsScrapAddParams assetsScrapAddParams);


    AssetsScrapVo getById(Long id);

}
