package com.zs.assets.depreciation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.assets.depreciation.domain.entity.AssetsDepreciationDetailsEntity;
import com.zs.assets.depreciation.domain.entity.AssetsDepreciationEntity;
import com.zs.assets.depreciation.domain.params.AssetsDepreciationAddParams;
import com.zs.assets.depreciation.domain.params.AssetsDepreciationQueryParams;
import com.zs.assets.depreciation.domain.vo.AssetsDepreciationVo;
import com.zs.assets.depreciation.mapper.AssetsDepreciationMapper;
import com.zs.assets.depreciation.service.IAssetsDepreciationDetailsService;
import com.zs.assets.depreciation.service.IAssetsDepreciationService;
import com.zs.assets.info.domain.dto.AssetsInfoDTO;
import com.zs.assets.info.domain.entity.AssetsInfoEntity;
import com.zs.assets.info.service.AssetsInfoService;
import com.zs.common.core.config.ThreadPoolConfig;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import jakarta.annotation.Resource;
import org.apache.commons.compress.utils.Lists;
import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 资产折旧
 *
 * @author 86740
 */
@Service
public class AssetsDepreciationServiceImpl extends ServiceImpl<AssetsDepreciationMapper, AssetsDepreciationEntity> implements IAssetsDepreciationService {

    @Resource
    private AssetsInfoService assetsInfoService;

    @Resource
    private IAssetsDepreciationDetailsService assetsDepreciationDetailsService;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public PageResult<AssetsDepreciationVo> page(AssetsDepreciationQueryParams assetsDepreciationQueryParams) {
        Page<AssetsDepreciationEntity> page = new PageInfo<>(assetsDepreciationQueryParams);
        IPage<AssetsDepreciationEntity> iPage = baseMapper.selectPage(page, getWrapper(assetsDepreciationQueryParams));

        return new PageResult<>(BeanUtil.copyToList(iPage.getRecords(), AssetsDepreciationVo.class), page.getTotal(), AssetsDepreciationVo.class);
    }

    public QueryWrapper<AssetsDepreciationEntity> getWrapper(AssetsDepreciationQueryParams assetsDepreciationQueryParams) {
        QueryWrapper<AssetsDepreciationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Strings.isNotEmpty(assetsDepreciationQueryParams.getName()), "name", assetsDepreciationQueryParams.getName());
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void oneKeyDepreciation(AssetsDepreciationAddParams assetsDepreciationAddParams) {

            // 判断是否已经提折旧了
            boolean exists = this.baseMapper.exists(Wrappers.<AssetsDepreciationEntity>lambdaQuery().eq(AssetsDepreciationEntity::getName, DateUtil.format(new Date(), "yyyyMM")));

            if (exists){
                throw new ZsException("本月已经提折旧了，请勿重复提折旧。");
            }

        try{
            // 除了无形资产、土地使用权、文物和陈列品、图书和档案。就这四类不需要提折旧吧，其他都进行折旧。
            // 只要入库的且不报废的都提折旧
            // 获取所有待提折旧的资产
            System.out.println("开始" + DateUtil.now());
            List<AssetsInfoEntity>  assetsInfoDTOList = assetsInfoService.getDepreciationList();
            // 主表内容
            AssetsDepreciationEntity assetsDepreciationEntity = saveAssetsDepreciation(assetsInfoDTOList.size(), new BigDecimal("0.00"));
            this.baseMapper.insert(assetsDepreciationEntity);
            // 明细表内容
            BigDecimal amountPrice = saveAssetsDepreciationDetails(assetsDepreciationEntity.getId(), assetsInfoDTOList);
            assetsDepreciationEntity.setAmountPrice(amountPrice);

            this.baseMapper.updateById(assetsDepreciationEntity);
            System.out.println("结束：" + DateUtil.now());
        }catch (Exception e){
            if (e instanceof DuplicateKeyException){
                throw new ZsException("本月已经提折旧了，请勿重复提折旧11。");
            }else {
                throw new ZsException("折旧失败");
            }

        }

    }

    public AssetsDepreciationEntity saveAssetsDepreciation(Integer detailsSize, BigDecimal amountPrice){
        AssetsDepreciationEntity assetsDepreciationEntity = new AssetsDepreciationEntity();
        assetsDepreciationEntity.setName(DateUtil.format(new Date(), "yyyyMM"));
        assetsDepreciationEntity.setAmount(detailsSize);
        assetsDepreciationEntity.setAmountPrice(amountPrice);
        assetsDepreciationEntity.setCreateDate(new Date());
        return assetsDepreciationEntity;
    }


    public BigDecimal saveAssetsDepreciationDetails(Long depreciationId, List<AssetsInfoEntity> list) {
        // 初始化累计折旧金额
        AtomicReference<BigDecimal> amountPrice = new AtomicReference<>(BigDecimal.ZERO);

        // 准备保存的折旧实体列表
        List<AssetsDepreciationDetailsEntity> detailsEntities = list.stream()
                .map(dto -> saveAssetsDepreciationDetailsEntity(depreciationId, dto, amountPrice))
                .collect(Collectors.toList());
        threadPoolTaskExecutor.execute(() -> batchSave(detailsEntities, assetsDepreciationDetailsService::saveBatch));
        List<AssetsInfoEntity> updatedAssetsInfoDTOs = updateAssetsInfoDTOs(list);
        threadPoolTaskExecutor.execute(() -> batchSave(updatedAssetsInfoDTOs, batch -> assetsInfoService.updateBatchById(batch)));
        return amountPrice.get();
    }





    private static AssetsDepreciationDetailsEntity saveAssetsDepreciationDetailsEntity(Long depreciationId, AssetsInfoEntity dto, AtomicReference<BigDecimal> amountPrice) {
        AssetsDepreciationDetailsEntity detailsEntity = new AssetsDepreciationDetailsEntity();
        detailsEntity.setDepreciationId(depreciationId);
        detailsEntity.setAssetsSerialNo(dto.getSerialNo());
        detailsEntity.setManageOrgId(dto.getManageOrgId());
        detailsEntity.setUseOrgId(dto.getUseOrgId());
        detailsEntity.setTopLevelGbClassicCode(String.valueOf(dto.getTopLevelGbClassicId()));
        detailsEntity.setSchoolClassicId(dto.getClassicId());
        detailsEntity.setOriginalPrice(dto.getBuyPrice());
        detailsEntity.setNum(1);

        // 计算当月折旧金额
        BigDecimal monthlyDepreciation = calculateDepreciation(dto);

        detailsEntity.setAmount(monthlyDepreciation);
        amountPrice.set(amountPrice.get().add(monthlyDepreciation));
        // 设置累计折旧和月数
        detailsEntity.setAccumulatedDepreciation(dto.getDepreciatedPrice().add(monthlyDepreciation));
        detailsEntity.setAccumulatedDepreciationMonths(dto.getDepreciatedMonths() + 1);
        detailsEntity.setDepreciationCode(dto.getDepreciatedMonths() + 1 == dto.getDepreciationMonths() ? "depre3" : "depre1");
        detailsEntity.setIsCurrentPeriod(1);
        return detailsEntity;
    }


    private static BigDecimal calculateDepreciation(AssetsInfoEntity dto) {

        // 如果已折旧月数和可折旧月数相等，直接不参加折旧，返回0
        if(dto.getDepreciatedMonths().compareTo(dto.getDepreciationMonths()) == 0){
            return new BigDecimal(0);
        }
        // 如果已折旧月数 + 1 等于可折旧月数。说明为最后一个月的提折旧。如果不是最后一个月，直接返回预定的月折旧额
        if (dto.getDepreciatedMonths() + 1 < dto.getDepreciationMonths()) {
            return dto.getMonthlyDepreciationPrice();
        }

        // 最后一个月的处理,直接总的入库金额减去已折旧的金额
        return dto.getBuyPrice().subtract(dto.getDepreciatedPrice());
    }

    private List<AssetsInfoEntity> updateAssetsInfoDTOs(List<AssetsInfoEntity> assetsInfoDTOList) {
        return assetsInfoDTOList.stream()
                .peek(dto -> {
                    // 计算当月折旧金额
                    BigDecimal monthlyDepreciation = calculateDepreciation(dto);
                    // 更新当月折旧金额
                    dto.setCurrentPeriodDepreciatedPrice(monthlyDepreciation);
                    // 更新已折旧的金额。已折旧的金额=已折旧的金额+当月折旧金额
                    dto.setDepreciatedPrice(dto.getDepreciatedPrice().add(monthlyDepreciation));
                    // 更新已折旧的月数。已折旧的月数=已折旧的月数+1
                    dto.setDepreciatedMonths(dto.getDepreciatedMonths() + 1);
                    // 更新折旧状态。如果已折旧的月数=总折旧月数，则修改状态为折旧完成。
                    if (dto.getDepreciatedMonths() + 1 == dto.getDepreciationMonths()) {
                        dto.setDepreciationCode("depre3");
                    }
                })
                .collect(Collectors.toList());
    }

    private <T> void batchSave(List<T> list, Consumer<List<T>> batchConsumer) {
        List<List<T>> partitions = partition(list, 1000);
        partitions.forEach(batchConsumer);
    }
    public static <T> List<List<T>> partition(List<T> list, int size) {
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(new ArrayList<>(list.subList(i, Math.min(i + size, list.size()))));
        }
        return partitions;
    }
}
