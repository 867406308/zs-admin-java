package com.zs.modules.sys.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.log.params.SysLogErrorAddParams;
import com.zs.common.core.log.service.ILogErrorAspectService;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.modules.sys.log.domain.entity.SysLogErrorEntity;
import com.zs.modules.sys.log.domain.params.SysLogErrorQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogErrorVo;
import com.zs.modules.sys.log.mapper.SysLogErrorMapper;
import com.zs.modules.sys.log.service.ISysLogErrorService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author 86740
 */
@Service
public class SysLogErrorServiceImpl extends ServiceImpl<SysLogErrorMapper, SysLogErrorEntity> implements ISysLogErrorService, ILogErrorAspectService {
    @Override
    public void save(SysLogErrorAddParams sysLogErrorAddParams) {
        SysLogErrorEntity sysLogErrorEntity = BeanUtil.copyProperties(sysLogErrorAddParams, SysLogErrorEntity.class);
        baseMapper.insert(sysLogErrorEntity);
    }


    @NotNull
    @Override
    public PageResult<SysLogErrorVo> page(@NotNull SysLogErrorQueryParams sysLogErrorQueryParams) {

        Page<SysLogErrorEntity> page = new PageInfo<>(sysLogErrorQueryParams);
        IPage<SysLogErrorEntity> iPage = baseMapper.selectPage(page, getWrapper(sysLogErrorQueryParams));

        return new PageResult<>(BeanUtil.copyToList(iPage.getRecords(), SysLogErrorVo.class), page.getTotal(), SysLogErrorVo.class);
    }

    @Nullable
    @Override
    public List<SysLogErrorVo> list(@NotNull SysLogErrorQueryParams sysLogErrorQueryParams) {
        return BeanUtil.copyToList(baseMapper.selectList(getWrapper(sysLogErrorQueryParams)), SysLogErrorVo.class);
    }

    @NotNull
    public QueryWrapper<SysLogErrorEntity> getWrapper(@NotNull SysLogErrorQueryParams sysLogErrorQueryParams) {
        QueryWrapper<SysLogErrorEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Strings.isNotEmpty(sysLogErrorQueryParams.getUsername()), "username", sysLogErrorQueryParams.getUsername());
        wrapper.eq(Strings.isNotEmpty(sysLogErrorQueryParams.getIpAddress()), "ip_address", sysLogErrorQueryParams.getIpAddress());
        wrapper.like(Strings.isNotEmpty(sysLogErrorQueryParams.getModule()), "module", sysLogErrorQueryParams.getModule());
        wrapper.orderByDesc("create_time");

        return wrapper;
    }
}
