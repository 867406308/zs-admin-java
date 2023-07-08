package com.zs.modules.sys.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.model.params.SysLogLoginAddParams;
import com.zs.common.page.PageInfo;
import com.zs.common.page.PageResult;
import com.zs.framework.service.ILogLoginAspectService;
import com.zs.modules.sys.log.domain.entity.SysLogLoginEntity;
import com.zs.modules.sys.log.domain.params.SysLogLoginQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogLoginVo;
import com.zs.modules.sys.log.mapper.SysLogLoginMapper;
import com.zs.modules.sys.log.service.ISysLogLoginService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SysLogLoginServiceImpl extends ServiceImpl<SysLogLoginMapper, SysLogLoginEntity>
        implements ISysLogLoginService, ILogLoginAspectService {


    @Override
    public void save(SysLogLoginAddParams sysLogLoginAddParams) {
        SysLogLoginEntity sysLogLoginEntity = BeanUtil.copyProperties(sysLogLoginAddParams, SysLogLoginEntity.class);
        baseMapper.insert(sysLogLoginEntity);
    }

    @Override
    public PageResult<SysLogLoginVo> page(SysLogLoginQueryParams sysLogLoginQueryParams) {
        Page<SysLogLoginEntity> page = new PageInfo<>(sysLogLoginQueryParams);
        IPage<SysLogLoginEntity> iPage = baseMapper.selectPage(page, getWrapper(sysLogLoginQueryParams));

        return new PageResult<>(BeanUtil.copyToList(iPage.getRecords(), SysLogLoginVo.class), page.getTotal(), SysLogLoginVo.class);
    }

    public QueryWrapper<SysLogLoginEntity> getWrapper(SysLogLoginQueryParams sysLogLoginQueryParams) {
        QueryWrapper<SysLogLoginEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysLogLoginQueryParams.getLoginStatus()), "login_status", sysLogLoginQueryParams.getLoginStatus());
        wrapper.eq(Strings.isNotEmpty(sysLogLoginQueryParams.getUsername()), "username", sysLogLoginQueryParams.getUsername());
        wrapper.eq(Strings.isNotEmpty(sysLogLoginQueryParams.getIpAddress()), "ip_address", sysLogLoginQueryParams.getIpAddress());

        return wrapper;
    }

}
