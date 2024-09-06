package com.zs.assets.allot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.assets.allot.domain.entity.AssetsAllotDetailsEntity;
import com.zs.assets.allot.domain.params.AssetsAllotDetailsAddParams;
import com.zs.assets.allot.domain.vo.AssetsAllotDetailsVo;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * @author 86740
 */
public interface AssetsAllotDetailsService extends IService<AssetsAllotDetailsEntity> {

    @Nullable
    List<AssetsAllotDetailsVo> getAllotDetails(Long allotId);

    void  saveAllotDetails(Long allotId, List<AssetsAllotDetailsAddParams> assetsAllotDetailsEntityList);
}
