package com.zs.modules.sys.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.model.params.SysLogOperationAddParams;
import com.zs.common.page.PageInfo;
import com.zs.common.page.PageResult;
import com.zs.framework.service.ILogOperationAspectService;
import com.zs.modules.sys.log.domain.entity.SysLogOperationEntity;
import com.zs.modules.sys.log.domain.params.SysLogOperationQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogOperationVo;
import com.zs.modules.sys.log.mapper.SysLogOperationMapper;
import com.zs.modules.sys.log.service.ISysLogOperationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

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
