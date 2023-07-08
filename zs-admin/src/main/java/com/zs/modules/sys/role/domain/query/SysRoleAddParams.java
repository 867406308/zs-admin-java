package com.zs.modules.sys.role.domain.query;

import lombok.Data;

@Data
public class SysRoleAddParams {

    private String sysRoleId;
    private String roleName;
    private Integer sort;
    private Integer status;
    private String remark;
}
