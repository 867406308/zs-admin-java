package com.zs.assets.scrap.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.assets.scrap.domain.entity.AssetsScrapDetailsEntity;
import com.zs.assets.scrap.domain.entity.AssetsScrapEntity;
import com.zs.assets.scrap.domain.params.AssetsScrapAddParams;
import com.zs.assets.scrap.domain.params.AssetsScrapQueryParams;
import com.zs.assets.scrap.domain.vo.AssetsScrapVo;
import com.zs.assets.scrap.mapper.AssetsScrapMapper;
import com.zs.assets.scrap.service.AssetsScrapDetailsService;
import com.zs.assets.scrap.service.AssetsScrapService;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 86740
 */
@Service
public class AssetsScrapServiceImpl extends ServiceImpl<AssetsScrapMapper, AssetsScrapEntity> implements AssetsScrapService {

    @Resource
    private AssetsScrapDetailsService assetsScrapDetailsService;

    @Override
    public PageResult<AssetsScrapVo> page(AssetsScrapQueryParams assetsScrapQueryParams) {
        Page<AssetsScrapEntity> page = new PageInfo<>(assetsScrapQueryParams);
        IPage<AssetsScrapEntity> iPage = baseMapper.selectPage(page, getWrapper(assetsScrapQueryParams));

        return new PageResult<>(BeanUtil.copyToList(iPage.getRecords(), AssetsScrapVo.class), page.getTotal(), AssetsScrapVo.class);
    }

    public QueryWrapper<AssetsScrapEntity> getWrapper(AssetsScrapQueryParams assetsScrapQueryParams) {
        QueryWrapper<AssetsScrapEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Strings.isNotEmpty(assetsScrapQueryParams.getSerialNo()), "serial_no", assetsScrapQueryParams.getSerialNo());
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AssetsScrapAddParams assetsScrapAddParams) {
        AssetsScrapEntity assetsScrapEntity = BeanUtil.copyProperties(assetsScrapAddParams, AssetsScrapEntity.class);
        assetsScrapEntity.setSerialNo(getMaxSerialNo());
        assetsScrapEntity.setScrapDate(new Date());
        baseMapper.insert(assetsScrapEntity);

        List<String> detailsList = assetsScrapAddParams.getSerialNoList();


        if (detailsList == null || detailsList.isEmpty()) {
            return; // 直接返回，不需要执行后续操作
        }


        AssetsScrapDetailsEntity temp = new AssetsScrapDetailsEntity();
        temp.setScrapId(assetsScrapEntity.getId());
        List<AssetsScrapDetailsEntity> assetsScrapDetailsEntityList = new ArrayList<>(detailsList.size());
        for (String serialNo : detailsList) {
            temp.setAssetsSerialNo(serialNo);
            assetsScrapDetailsEntityList.add(temp.clone());
        }

        assetsScrapDetailsService.saveBatch(assetsScrapDetailsEntityList);

    }

    public String getMaxSerialNo() {
        // 查询数据库中serialNo最大的值，+1做为新的serialNo
        String maxSerialNo = baseMapper.getMaxSerialNo();
        Long serialNo = Long.parseLong(maxSerialNo) + 1;
        return String.valueOf(serialNo);
    }


    @Override
    public AssetsScrapVo getById(Long id) {
        return BeanUtil.copyProperties(baseMapper.selectById(id), AssetsScrapVo.class);
    }
}
