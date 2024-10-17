package com.zs.modules.sys.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.log.params.SysLogLoginAddParams;
import com.zs.common.core.log.service.ILogLoginAspectService;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.modules.sys.log.domain.entity.SysLogLoginEntity;
import com.zs.modules.sys.log.domain.params.SysLogLoginQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogLoginVo;
import com.zs.modules.sys.log.mapper.SysLogLoginMapper;
import com.zs.modules.sys.log.service.ISysLogLoginService;
import org.apache.logging.log4j.util.Strings;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 86740
 */
@Service
public class SysLogLoginServiceImpl extends ServiceImpl<SysLogLoginMapper, SysLogLoginEntity> implements ISysLogLoginService, ILogLoginAspectService {



    @Override
    public void save(SysLogLoginAddParams sysLogLoginAddParams) {
        SysLogLoginEntity sysLogLoginEntity = BeanUtil.copyProperties(sysLogLoginAddParams, SysLogLoginEntity.class);
        baseMapper.insert(sysLogLoginEntity);



    }

    @NotNull
    @Override
    public PageResult<SysLogLoginVo> page(@NotNull SysLogLoginQueryParams sysLogLoginQueryParams) {
        Page<SysLogLoginEntity> page = new PageInfo<>(sysLogLoginQueryParams);
        IPage<SysLogLoginEntity> iPage = baseMapper.selectPage(page, getWrapper(sysLogLoginQueryParams));

        return new PageResult<>(BeanUtil.copyToList(iPage.getRecords(), SysLogLoginVo.class), page.getTotal(), SysLogLoginVo.class);
    }

    @Nullable
    @Override
    public List<SysLogLoginVo> list(@NotNull SysLogLoginQueryParams sysLogLoginQueryParams) {
        List<SysLogLoginEntity> entities = baseMapper.selectList(getWrapper(sysLogLoginQueryParams));
        return BeanUtil.copyToList(entities, SysLogLoginVo.class);
    }

    @Nullable
    @Override
    public List<SysLogLoginVo> todayList() {
        List<SysLogLoginEntity> entities = baseMapper.selectList(new QueryWrapper<SysLogLoginEntity>().between("login_time", DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date())));
           return BeanUtil.copyToList(entities, SysLogLoginVo.class);
    }

    @NotNull
    public QueryWrapper<SysLogLoginEntity> getWrapper(@NotNull SysLogLoginQueryParams sysLogLoginQueryParams) {
        QueryWrapper<SysLogLoginEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysLogLoginQueryParams.getLoginStatus()), "login_status", sysLogLoginQueryParams.getLoginStatus());
        wrapper.eq(Strings.isNotEmpty(sysLogLoginQueryParams.getUsername()), "username", sysLogLoginQueryParams.getUsername());
        wrapper.eq(Strings.isNotEmpty(sysLogLoginQueryParams.getIpAddress()), "ip_address", sysLogLoginQueryParams.getIpAddress());
        wrapper.orderByDesc("login_time");

        return wrapper;
    }

}
