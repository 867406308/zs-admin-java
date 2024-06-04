package com.zs.assets.allot.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.assets.allot.domain.entity.AssetsAllotEntity;
import com.zs.assets.allot.domain.params.AssetsAllotQueryParams;
import com.zs.assets.allot.domain.vo.AssetsAllotVo;
import com.zs.assets.allot.mapper.AssetsAllotMapper;
import com.zs.assets.allot.service.AssetsAllotDetailsService;
import com.zs.assets.allot.service.AssetsAllotService;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86740
 */
@Service
public class AssetsAllotServiceImpl extends ServiceImpl<AssetsAllotMapper, AssetsAllotEntity> implements AssetsAllotService {



    @Override
    public PageResult<AssetsAllotVo> page(AssetsAllotQueryParams assetsAllotQueryParams) {
        Page<AssetsAllotEntity> page = new PageInfo<>(assetsAllotQueryParams);
        IPage<AssetsAllotEntity> iPage = baseMapper.selectPage(page, getWrapper(assetsAllotQueryParams));

        List<AssetsAllotVo> list = BeanUtil.copyToList(iPage.getRecords(), AssetsAllotVo.class);

        return new PageResult<>(list, page.getTotal(), AssetsAllotVo.class);
    }

    public QueryWrapper<AssetsAllotEntity> getWrapper(AssetsAllotQueryParams assetsAllotQueryParams) {
        QueryWrapper<AssetsAllotEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Strings.isNotEmpty(assetsAllotQueryParams.getSerialNo()), "serial_no", assetsAllotQueryParams.getSerialNo());
        return queryWrapper;
    }


}
