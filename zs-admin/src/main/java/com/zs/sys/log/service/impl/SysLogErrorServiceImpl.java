package com.zs.sys.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.log.params.SysLogErrorAddParams;
import com.zs.common.core.log.service.ILogErrorAspectService;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.sys.log.domain.entity.SysLogErrorEntity;
import com.zs.sys.log.domain.params.SysLogErrorQueryParams;
import com.zs.sys.log.domain.vo.SysLogErrorVo;
import com.zs.sys.log.mapper.SysLogErrorMapper;
import com.zs.sys.log.service.ISysLogErrorService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;


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


    @Override
    public PageResult<SysLogErrorVo> page(SysLogErrorQueryParams sysLogErrorQueryParams) {

        Page<SysLogErrorEntity> page = new PageInfo<>(sysLogErrorQueryParams);
        IPage<SysLogErrorEntity> iPage = baseMapper.selectPage(page, getWrapper(sysLogErrorQueryParams));

        return new PageResult<>(BeanUtil.copyToList(iPage.getRecords(), SysLogErrorVo.class), page.getTotal(), SysLogErrorVo.class);
    }

    public QueryWrapper<SysLogErrorEntity> getWrapper(SysLogErrorQueryParams sysLogErrorQueryParams) {
        QueryWrapper<SysLogErrorEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Strings.isNotEmpty(sysLogErrorQueryParams.getUsername()), "username", sysLogErrorQueryParams.getUsername());
        wrapper.eq(Strings.isNotEmpty(sysLogErrorQueryParams.getIpAddress()), "ip_address", sysLogErrorQueryParams.getIpAddress());

        return wrapper;
    }
}
