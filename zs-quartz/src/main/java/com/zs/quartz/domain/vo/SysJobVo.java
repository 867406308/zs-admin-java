package com.zs.quartz.domain.vo;

import lombok.Data;

/**
 * 定时任务视图对象
 */
@Data
public class SysJobVo {

    private Long sysJobId;
    private String jobClass;
    private String jobName;
    private String jobGroup;
    private String cronExpression;
    private Integer status;
    private String remark;
    private String createTime;
}
