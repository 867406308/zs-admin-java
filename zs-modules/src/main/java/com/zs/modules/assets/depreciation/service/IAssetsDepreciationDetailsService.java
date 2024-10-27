package com.zs.modules.assets.depreciation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.modules.assets.depreciation.domain.entity.AssetsDepreciationDetailsEntity;
import com.zs.modules.assets.depreciation.domain.params.AssetsDepreciationDetailsQueryParams;
import com.zs.modules.assets.depreciation.domain.vo.AssetsDepreciationDetailsVo;

/**
 * 资产折旧明细
 *
 * @author 86740
 */
public interface IAssetsDepreciationDetailsService extends IService<AssetsDepreciationDetailsEntity> {

    PageResult<AssetsDepreciationDetailsVo> page(AssetsDepreciationDetailsQueryParams assetsDepreciationDetailsQueryParams);

}
