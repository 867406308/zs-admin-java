package com.zs.sys.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.log.params.SysLogOperationAddParams;
import com.zs.common.core.log.service.ILogOperationAspectService;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.sys.log.domain.entity.SysLogOperationEntity;
import com.zs.sys.log.domain.params.SysLogOperationQueryParams;
import com.zs.sys.log.domain.vo.SysLogOperationVo;
import com.zs.sys.log.mapper.SysLogOperationMapper;
import com.zs.sys.log.service.ISysLogOperationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

/**
 * @author 86740
 */
@Service
public class SysLogOperationServiceImpl extends ServiceImpl<SysLogOperationMapper, SysLogOperationEntity>
        implements ISysLogOperationService, ILogOperationAspectService {
    @Override
    public void save(SysLogOperationAddParams sysLogOperationAddParams) {
        SysLogOperationEntity sysLogOperationEntity = BeanUtil.copyProperties(sysLogOperationAddParams, SysLogOperationEntity.class);
        baseMapper.insert(sysLogOperationEntity);
    }

    @Override
    public PageResult<SysLogOperationVo> page(SysLogOperationQueryParams sysLogOperationQueryParams) {
        Page<SysLogOperationEntity> page = new PageInfo<>(sysLogOperationQueryParams);
        IPage<SysLogOperationEntity> iPage = baseMapper.selectPage(page, getWrapper(sysLogOperationQueryParams));

        return new PageResult<>(BeanUtil.copyToList(iPage.getRecords(), SysLogOperationVo.class), page.getTotal(), SysLogOperationVo.class);
    }

    public QueryWrapper<SysLogOperationEntity> getWrapper(SysLogOperationQueryParams sysLogOperationQueryParams) {
        QueryWrapper<SysLogOperationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Strings.isNotEmpty(sysLogOperationQueryParams.getUsername()), "username", sysLogOperationQueryParams.getUsername());
        wrapper.eq(Strings.isNotEmpty(sysLogOperationQueryParams.getIpAddress()), "ip_address", sysLogOperationQueryParams.getIpAddress());

        return wrapper;
    }
}
