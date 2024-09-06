package com.zs.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.quartz.domain.entity.SysJobLogEntity;
import com.zs.quartz.mapper.SysJobLogMapper;
import com.zs.quartz.service.ISysJobLogService;
import org.springframework.stereotype.Service;

/**
 * 定时任务日志service实现
 */
@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLogEntity> implements ISysJobLogService {

    @Override
    public void addJobLog(SysJobLogEntity sysJobLogEntity) {
        this.baseMapper.insert(sysJobLogEntity);
    }
}
