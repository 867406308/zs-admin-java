package com.zs.modules.assets.allot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.modules.assets.allot.domain.entity.AssetsAllotEntity;
import com.zs.modules.assets.allot.domain.params.AssetsAllotAddParams;
import com.zs.modules.assets.allot.domain.params.AssetsAllotQueryParams;
import com.zs.modules.assets.allot.domain.vo.AssetsAllotVo;

/**
 * @author 86740
 */
public interface AssetsAllotService extends IService<AssetsAllotEntity> {

    PageResult<AssetsAllotVo> page(AssetsAllotQueryParams assetsAllotQueryParams);

    void save(AssetsAllotAddParams assetsAllotAddParams);
}
