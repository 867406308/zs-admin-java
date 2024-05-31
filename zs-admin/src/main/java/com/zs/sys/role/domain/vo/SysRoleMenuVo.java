package com.zs.sys.role.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 86740
 */
@Data
public class SysRoleMenuVo implements Serializable {

    private Long sysRoleMenuId;

    private Long sysRoleId;

    private Long sysMenuId;
}
