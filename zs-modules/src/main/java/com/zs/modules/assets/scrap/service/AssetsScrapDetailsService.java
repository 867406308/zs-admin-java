package com.zs.modules.assets.scrap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.modules.assets.scrap.domain.entity.AssetsScrapDetailsEntity;
import com.zs.modules.assets.scrap.domain.params.AssetsScrapDetailsQueryParams;
import com.zs.modules.assets.scrap.domain.vo.AssetsScrapDetailsVo;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * @author 86740
 */
public interface AssetsScrapDetailsService extends IService<AssetsScrapDetailsEntity> {


    PageResult<AssetsScrapDetailsVo> page(AssetsScrapDetailsQueryParams assetsScrapDetailsQueryParams);


    @Nullable
    List<AssetsScrapDetailsVo> list(AssetsScrapDetailsQueryParams assetsScrapDetailsQueryParams);

    void saveBatch(List<AssetsScrapDetailsEntity> list);

}
