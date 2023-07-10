package com.zs.modules.sys.log.domain.params;

import com.zs.common.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLogOperationQueryParams extends BasePageParams {

    private String username;
    private String ipAddress;
}
