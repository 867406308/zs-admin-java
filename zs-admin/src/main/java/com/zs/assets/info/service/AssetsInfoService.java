package com.zs.assets.info.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.assets.info.domain.dto.AssetsInfoDTO;
import com.zs.assets.info.domain.entity.AssetsInfoEntity;
import com.zs.assets.info.domain.query.AssetsInfoAddParams;
import com.zs.assets.info.domain.query.AssetsInfoQueryParams;
import com.zs.assets.info.domain.query.AssetsInfoSerialNoImportParams;
import com.zs.assets.info.domain.query.AssetsInfoStockInParams;
import com.zs.assets.info.domain.vo.AssetsInfoVo;
import com.zs.common.core.page.PageResult;

import java.util.List;


/**
 * @author 86740
 */
public interface AssetsInfoService extends IService<AssetsInfoEntity> {


    PageResult<AssetsInfoVo> page(AssetsInfoQueryParams assetsInfoQueryParams);

    /**
     * 根据资产编号批量查询资产信息
     **/
    List<AssetsInfoVo> getBySerialNoList(AssetsInfoSerialNoImportParams assetsInfoSerialNoImportParams);

    void save(AssetsInfoAddParams assetsInfoAddParams);


    AssetsInfoVo getById(Long id);

    void stockIn(AssetsInfoStockInParams assetsInfoStockInParams);

    /**
     * 更新资产使用状态
     **/
    void updateUseStatusCode(List<String> serialNoList);

    /**
     * 根据使用部门、资产级别查询资产信息
     */
    List<AssetsInfoVo> getList(Long useOrgId, Long levelId);

    /**
     * 根据资产编号列表查询资产信息
     */
    List<AssetsInfoVo> getList(List<String> serialNoList);

    /**
     * 获取所有待提折旧的资产信息
     */
    List<AssetsInfoEntity> getDepreciationList();


}
