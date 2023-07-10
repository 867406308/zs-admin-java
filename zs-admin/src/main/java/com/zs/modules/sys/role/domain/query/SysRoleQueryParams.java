package com.zs.modules.sys.role.domain.query;

import com.zs.common.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleQueryParams extends BasePageParams implements Serializable {

    private String roleName;
}
