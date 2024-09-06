package com.zs.quartz.domain.params;

import lombok.Data;

/**
 * 定时任务新增参数
 */
@Data
public class SysJobAddParams {

    private String jobClass;
    private String jobName;
    private String jobGroup;
    private String cronExpression;
    private Integer status;
    private String remark;
}
