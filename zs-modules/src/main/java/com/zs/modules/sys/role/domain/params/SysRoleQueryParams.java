package com.zs.modules.sys.role.domain.params;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleQueryParams extends BasePageParams {

    private String roleName;
}
