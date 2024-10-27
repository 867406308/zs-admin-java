package com.zs.modules.sys.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.log.params.SysLogOperationAddParams;
import com.zs.common.core.log.service.ILogOperationAspectService;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.modules.sys.log.domain.entity.SysLogOperationEntity;
import com.zs.modules.sys.log.domain.params.SysLogOperationQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogOperationVo;
import com.zs.modules.sys.log.mapper.SysLogOperationMapper;
import com.zs.modules.sys.log.service.ISysLogOperationService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @NotNull
    @Override
    public PageResult<SysLogOperationVo> page(@NotNull SysLogOperationQueryParams sysLogOperationQueryParams) {
        Page<SysLogOperationEntity> page = new PageInfo<>(sysLogOperationQueryParams);
        IPage<SysLogOperationEntity> iPage = baseMapper.selectPage(page, getWrapper(sysLogOperationQueryParams));

        return new PageResult<>(BeanUtil.copyToList(iPage.getRecords(), SysLogOperationVo.class), page.getTotal(), SysLogOperationVo.class);
    }

    @Nullable
    @Override
    public List<SysLogOperationVo> list(@NotNull SysLogOperationQueryParams sysLogOperationQueryParams) {
        return BeanUtil.copyToList(baseMapper.selectList(getWrapper(sysLogOperationQueryParams)), SysLogOperationVo.class);
    }

    @NotNull
    public QueryWrapper<SysLogOperationEntity> getWrapper(@NotNull SysLogOperationQueryParams sysLogOperationQueryParams) {
        QueryWrapper<SysLogOperationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Strings.isNotEmpty(sysLogOperationQueryParams.getUsername()), "username", sysLogOperationQueryParams.getUsername());
        wrapper.eq(Strings.isNotEmpty(sysLogOperationQueryParams.getIpAddress()), "ip_address", sysLogOperationQueryParams.getIpAddress());
        wrapper.like(Strings.isNotEmpty(sysLogOperationQueryParams.getModule()), "module", sysLogOperationQueryParams.getModule());
        wrapper.orderByDesc("create_time");
        return wrapper;
    }
}
