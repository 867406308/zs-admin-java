package com.zs.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.quartz.domain.entity.SysJobLogEntity;
import com.zs.quartz.domain.params.SysJobLogQueryParams;
import com.zs.quartz.domain.vo.SysJobLogVo;

import java.util.List;

/**
 * 定时任务日志service
 */
public interface ISysJobLogService extends IService<SysJobLogEntity> {

    void addJobLog(SysJobLogEntity sysJobLogEntity);

    PageResult<SysJobLogVo> page(SysJobLogQueryParams sysJobLogQueryParams);
}
