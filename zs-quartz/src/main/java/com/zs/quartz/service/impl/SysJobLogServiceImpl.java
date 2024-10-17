package com.zs.quartz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.quartz.domain.entity.SysJobLogEntity;
import com.zs.quartz.domain.params.SysJobLogQueryParams;
import com.zs.quartz.domain.vo.SysJobLogVo;
import com.zs.quartz.mapper.SysJobLogMapper;
import com.zs.quartz.service.ISysJobLogService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 定时任务日志service实现
 */
@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLogEntity> implements ISysJobLogService {

    @Override
    public void addJobLog(SysJobLogEntity sysJobLogEntity) {
        this.baseMapper.insert(sysJobLogEntity);
    }

    @Override
    public PageResult<SysJobLogVo> page(SysJobLogQueryParams sysJobLogQueryParams) {
        Page<SysJobLogEntity> page = new PageInfo<>(sysJobLogQueryParams);
        IPage<SysJobLogEntity> iPage = this.baseMapper.selectPage(page, getWrapper(sysJobLogQueryParams));
        return new PageResult<>(BeanUtil.copyToList(iPage.getRecords(), SysJobLogVo.class), page.getTotal(), SysJobLogVo.class);
    }

    public QueryWrapper<SysJobLogEntity> getWrapper(SysJobLogQueryParams sysJobLogQueryParams) {
        QueryWrapper<SysJobLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_job_id", sysJobLogQueryParams.getSysJobId());
        queryWrapper.eq(Strings.isNotEmpty(sysJobLogQueryParams.getJobName()), "job_name", sysJobLogQueryParams.getJobName());
        queryWrapper.eq(Strings.isNotEmpty(sysJobLogQueryParams.getJobGroup()), "job_group", sysJobLogQueryParams.getJobGroup());
        queryWrapper.eq(Objects.nonNull(sysJobLogQueryParams.getStatus()), "status", sysJobLogQueryParams.getStatus());
        return queryWrapper;
    }
}
