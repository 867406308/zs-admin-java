package com.zs.modules.sys.role.domain.query;

import com.zs.common.page.BasePageParams;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleQueryParams extends BasePageParams implements Serializable {

    private String roleName;
}
