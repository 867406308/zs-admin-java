package com.zs.modules.assets.allot.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.modules.assets.allot.domain.entity.AssetsAllotEntity;
import com.zs.modules.assets.allot.domain.params.AssetsAllotAddParams;
import com.zs.modules.assets.allot.domain.params.AssetsAllotQueryParams;
import com.zs.modules.assets.allot.domain.vo.AssetsAllotVo;
import com.zs.modules.assets.allot.mapper.AssetsAllotMapper;
import com.zs.modules.assets.allot.service.AssetsAllotDetailsService;
import com.zs.modules.assets.allot.service.AssetsAllotService;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 86740
 */
@Service
public class AssetsAllotServiceImpl extends ServiceImpl<AssetsAllotMapper, AssetsAllotEntity> implements AssetsAllotService {

    @Resource
    private AssetsAllotDetailsService assetsAllotDetailsService;


    @NotNull
    @Override
    public PageResult<AssetsAllotVo> page(@NotNull AssetsAllotQueryParams assetsAllotQueryParams) {
        Page<AssetsAllotEntity> page = new PageInfo<>(assetsAllotQueryParams);
        IPage<AssetsAllotEntity> iPage = baseMapper.selectPage(page, getWrapper(assetsAllotQueryParams));

        List<AssetsAllotVo> list = BeanUtil.copyToList(iPage.getRecords(), AssetsAllotVo.class);

        return new PageResult<>(list, page.getTotal(), AssetsAllotVo.class);
    }


    @NotNull
    public QueryWrapper<AssetsAllotEntity> getWrapper(@NotNull AssetsAllotQueryParams assetsAllotQueryParams) {
        QueryWrapper<AssetsAllotEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Strings.isNotEmpty(assetsAllotQueryParams.getSerialNo()), "serial_no", assetsAllotQueryParams.getSerialNo());
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(@NotNull AssetsAllotAddParams assetsAllotAddParams) {

        AssetsAllotEntity entity = new AssetsAllotEntity();
        entity.setSerialNo(generateSerialNumber());
        entity.setReason(assetsAllotAddParams.getReason());
        entity.setCreateDate(new Date());

        this.baseMapper.insert(entity);


        assetsAllotDetailsService.saveAllotDetails(entity.getId(), assetsAllotAddParams.getAssetsAllotDetails());
    }

    @NotNull
    private  String generateSerialNumber() {
        String datePart = DateUtil.format(new Date(), "yyyyMMdd");
        String maxSerialNo = this.baseMapper.getMaxSerialNo(datePart);
        int nextSerialNo;

        if (maxSerialNo != null && !maxSerialNo.isEmpty()) {
            // 提取序列号的数字部分并递增。
            nextSerialNo = Integer.parseInt(maxSerialNo.substring(8)) + 1;
        } else {
            // 如果该日期不存在先前的序列号，则从1开始。
            nextSerialNo = 1;
        }
        // 格式化序列号，添加前导零，并与日期部分拼接。
        return datePart + String.format("%04d", nextSerialNo);
    }



}
