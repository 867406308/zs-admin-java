package com.zs.quartz.domain.params;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 定时任务日志查询参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysJobLogQueryParams extends BasePageParams {

    private Long sysJobId;
    private String jobName;
    private String jobGroup;
    private Integer status;

}
