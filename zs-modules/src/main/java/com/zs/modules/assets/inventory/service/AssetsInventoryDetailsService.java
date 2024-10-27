package com.zs.modules.assets.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.modules.assets.inventory.domain.entity.AssetsInventoryDetailsEntity;
import com.zs.modules.assets.inventory.domain.params.AssetsInventoryDetailsQueryParams;
import com.zs.modules.assets.inventory.domain.vo.AssetsInventoryDetailsVo;

/**
 * @author 86740
 */
public interface AssetsInventoryDetailsService extends IService<AssetsInventoryDetailsEntity> {

    PageResult<AssetsInventoryDetailsVo> page(AssetsInventoryDetailsQueryParams assetsInventoryDetailsQueryParams);

}
