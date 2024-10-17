package com.zs.modules.assets.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.modules.assets.inventory.domain.entity.AssetsInventoryEntity;
import com.zs.modules.assets.inventory.domain.params.AssetsInventoryAddParams;
import com.zs.modules.assets.inventory.domain.params.AssetsInventoryQueryParams;
import com.zs.modules.assets.inventory.domain.vo.AssetsInventoryVo;
import com.zs.common.core.page.PageResult;

/**
 * @author 86740
 */
public interface AssetsInventoryService extends IService<AssetsInventoryEntity> {

    PageResult<AssetsInventoryVo> page(AssetsInventoryQueryParams assetsInventoryQueryParams);

    /**
     * 创建盘点任务
     */
    void createInventoryTask(AssetsInventoryAddParams assetsInventoryAddParams);

    AssetsInventoryVo get(Long id);
}
