package com.zs.assets.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.assets.inventory.domain.entity.AssetsInventoryDetailsEntity;
import com.zs.assets.inventory.domain.params.AssetsInventoryDetailsQueryParams;
import com.zs.assets.inventory.domain.vo.AssetsInventoryDetailsVo;
import com.zs.common.core.page.PageResult;

/**
 * @author 86740
 */
public interface AssetsInventoryDetailsService extends IService<AssetsInventoryDetailsEntity> {

    PageResult<AssetsInventoryDetailsVo> page(AssetsInventoryDetailsQueryParams assetsInventoryDetailsQueryParams);

}
