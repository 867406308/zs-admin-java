package com.zs.quartz.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 定时任务日志
 */
@Data
public class SysJobLogVo {

    private Long sysJobLogId;
    private Long sysJobId;
    private String jobClass;
    private String jobName;
    private String jobGroup;
    private String jobMessage;
    private int status;
    private String exceptionInfo;
    private Date startTime;
    private Date endTime;
    private Long duration;


}
