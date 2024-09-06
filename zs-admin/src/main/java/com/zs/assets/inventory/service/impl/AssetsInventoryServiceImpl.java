package com.zs.assets.inventory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.assets.info.domain.vo.AssetsInfoVo;
import com.zs.assets.info.service.AssetsInfoService;
import com.zs.assets.inventory.domain.entity.AssetsInventoryDetailsEntity;
import com.zs.assets.inventory.domain.entity.AssetsInventoryEntity;
import com.zs.assets.inventory.domain.params.AssetsInventoryAddParams;
import com.zs.assets.inventory.domain.params.AssetsInventoryQueryParams;
import com.zs.assets.inventory.domain.vo.AssetsInventoryVo;
import com.zs.assets.inventory.mapper.AssetsInventoryMapper;
import com.zs.assets.inventory.service.AssetsInventoryDetailsService;
import com.zs.assets.inventory.service.AssetsInventoryService;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 86740
 */
@Service
public class AssetsInventoryServiceImpl extends ServiceImpl<AssetsInventoryMapper, AssetsInventoryEntity> implements AssetsInventoryService {

    @Resource
    private AssetsInfoService assetsInfoService;
    @Resource
    private AssetsInventoryDetailsService assetsInventoryDetailsService;


    @NotNull
    @Override
    public PageResult<AssetsInventoryVo> page(@NotNull AssetsInventoryQueryParams assetsInventoryQueryParams) {
        Page<AssetsInventoryEntity> page = new PageInfo<>(assetsInventoryQueryParams);
        Map<String, Object> params = BeanUtil.beanToMap(assetsInventoryQueryParams);
        IPage<AssetsInventoryEntity> iPage = baseMapper.page(page, params);

        List<AssetsInventoryVo> list = BeanUtil.copyToList(iPage.getRecords(), AssetsInventoryVo.class);

        return new PageResult<>(list, page.getTotal(), AssetsInventoryVo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createInventoryTask(@NotNull AssetsInventoryAddParams assetsInventoryAddParams) {
        // 根据盘点部门、资产级别查询所有资产。盘点争对使用部门进行盘点

        List<AssetsInfoVo> list = assetsInfoService.getList(assetsInventoryAddParams.getOrgId(), assetsInventoryAddParams.getLevelId());

        if (list.isEmpty()) {
            throw new ZsException("该使用部门没有需要盘点的资产");
        }
        AssetsInventoryEntity assetsInventoryEntity = BeanUtil.copyProperties(assetsInventoryAddParams, AssetsInventoryEntity.class);
        assetsInventoryEntity.setCreateDate(new Date());
        this.baseMapper.insert(assetsInventoryEntity);

        // 创建盘点详情实体列表
        List<AssetsInventoryDetailsEntity> detailsList = list.stream().map(assetsInfoVo -> {
            AssetsInventoryDetailsEntity detailsEntity = new AssetsInventoryDetailsEntity();
            detailsEntity.setInventoryId(assetsInventoryEntity.getId());
            detailsEntity.setAssetsSerialNo(assetsInfoVo.getSerialNo());
            detailsEntity.setAssetsStatusCode(assetsInfoVo.getAssetsStatusCode());
            return detailsEntity;
        }).collect(Collectors.toList());


        extracted(detailsList);
    }

    @Override
    public AssetsInventoryVo get(Long id) {
        AssetsInventoryEntity assetsInventoryEntity = this.baseMapper.get(id);
        return BeanUtil.copyProperties(assetsInventoryEntity, AssetsInventoryVo.class);
    }

    private void extracted(@NotNull List<AssetsInventoryDetailsEntity> detailsList) {

        // 每次批量插入的数据量
        int batchSize = 1000;
        int listSize = detailsList.size();
        // 计算需要的批次数，向上取整
        int numBatches = (listSize + batchSize - 1) / batchSize;

        for (int i = 0; i < numBatches; i++) {
            // 计算当前批次的开始和结束索引
            int start = i * batchSize;
            int end = Math.min(start + batchSize, listSize);

            // 获取当前批次的数据
            List<AssetsInventoryDetailsEntity> batchDetails = detailsList.subList(start, end);

            // 执行批量插入操作
            assetsInventoryDetailsService.saveBatch(batchDetails);
        }
    }


}
