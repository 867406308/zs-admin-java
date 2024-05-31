package com.zs.assets.scrap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.assets.scrap.domain.entity.AssetsScrapDetailsEntity;
import com.zs.assets.scrap.domain.params.AssetsScrapDetailsQueryParams;
import com.zs.assets.scrap.domain.vo.AssetsScrapDetailsVo;
import com.zs.common.core.page.PageResult;

import java.util.List;

/**
 * @author 86740
 */
public interface AssetsScrapDetailsService extends IService<AssetsScrapDetailsEntity> {


    PageResult<AssetsScrapDetailsVo> page(AssetsScrapDetailsQueryParams assetsScrapDetailsQueryParams);


    List<AssetsScrapDetailsVo> list(AssetsScrapDetailsQueryParams assetsScrapDetailsQueryParams);

    void saveBatch(List<AssetsScrapDetailsEntity> list);

}
