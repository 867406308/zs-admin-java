package com.zs.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.quartz.domain.entity.SysJobEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务mapper
 */
@Mapper
public interface SysJobMapper extends BaseMapper<SysJobEntity> {
}
