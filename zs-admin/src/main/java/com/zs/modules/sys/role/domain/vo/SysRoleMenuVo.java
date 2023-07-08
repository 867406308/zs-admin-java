package com.zs.modules.sys.role.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleMenuVo implements Serializable {

    private Long sysRoleMenuId;

    private Long sysRoleId;

    private Long sysMenuId;
}
