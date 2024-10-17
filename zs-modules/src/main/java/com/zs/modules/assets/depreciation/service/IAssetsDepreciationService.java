package com.zs.modules.assets.depreciation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.modules.assets.depreciation.domain.entity.AssetsDepreciationEntity;
import com.zs.modules.assets.depreciation.domain.params.AssetsDepreciationAddParams;
import com.zs.modules.assets.depreciation.domain.params.AssetsDepreciationQueryParams;
import com.zs.modules.assets.depreciation.domain.vo.AssetsDepreciationVo;
import com.zs.common.core.page.PageResult;

/**
 * 资产折旧service
 *
 * @author 86740
 */
public interface IAssetsDepreciationService extends IService<AssetsDepreciationEntity> {


    PageResult<AssetsDepreciationVo> page(AssetsDepreciationQueryParams assetsDepreciationQueryParams);

    /**
     * 一键折旧
     **/
    void oneKeyDepreciation(AssetsDepreciationAddParams assetsDepreciationAddParams);

}
