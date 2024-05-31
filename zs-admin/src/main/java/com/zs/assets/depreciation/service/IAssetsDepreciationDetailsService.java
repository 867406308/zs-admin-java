package com.zs.assets.depreciation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.assets.depreciation.domain.entity.AssetsDepreciationDetailsEntity;
import com.zs.assets.depreciation.domain.params.AssetsDepreciationDetailsQueryParams;
import com.zs.assets.depreciation.domain.params.AssetsDepreciationQueryParams;
import com.zs.assets.depreciation.domain.vo.AssetsDepreciationDetailsVo;
import com.zs.assets.depreciation.domain.vo.AssetsDepreciationVo;
import com.zs.common.core.page.PageResult;

/**
 * 资产折旧明细
 *
 * @author 86740
 */
public interface IAssetsDepreciationDetailsService extends IService<AssetsDepreciationDetailsEntity> {

    PageResult<AssetsDepreciationDetailsVo> page(AssetsDepreciationDetailsQueryParams assetsDepreciationDetailsQueryParams);

}
