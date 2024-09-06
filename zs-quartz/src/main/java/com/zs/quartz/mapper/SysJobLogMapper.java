package com.zs.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.quartz.domain.entity.SysJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志mapper
 */
@Mapper
public interface SysJobLogMapper extends BaseMapper<SysJobLogEntity> {
}
