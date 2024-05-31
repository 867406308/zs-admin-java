package com.zs.sys.log.domain.params;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLogLoginQueryParams extends BasePageParams {

    private String username;
    private String ipAddress;
    private Integer loginStatus;
}
