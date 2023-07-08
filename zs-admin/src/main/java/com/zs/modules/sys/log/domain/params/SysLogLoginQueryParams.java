package com.zs.modules.sys.log.domain.params;

import com.zs.common.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class SysLogLoginQueryParams extends BasePageParams {

    private String username;
    private String ipAddress;
    private Integer loginStatus;
}
