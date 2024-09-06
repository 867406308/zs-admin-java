package com.zs.quartz.domain.params;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 定时任务查询参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysJobQueryParams extends BasePageParams {

    private String jobName;
    private String jobGroup;
    private Integer status;
}
