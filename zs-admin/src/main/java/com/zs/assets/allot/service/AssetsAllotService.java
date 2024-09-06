package com.zs.assets.allot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.assets.allot.domain.entity.AssetsAllotEntity;
import com.zs.assets.allot.domain.params.AssetsAllotAddParams;
import com.zs.assets.allot.domain.params.AssetsAllotQueryParams;
import com.zs.assets.allot.domain.vo.AssetsAllotVo;
import com.zs.common.core.page.PageResult;

/**
 * @author 86740
 */
public interface AssetsAllotService extends IService<AssetsAllotEntity> {

    PageResult<AssetsAllotVo> page(AssetsAllotQueryParams assetsAllotQueryParams);

    void save(AssetsAllotAddParams assetsAllotAddParams);
}
