package com.zs.sys.role.domain.params;

import lombok.Data;

import java.util.List;

/**
 * @author 86740
 */
@Data
public class SysRoleAddParams {

    private Long sysRoleId;
    private String roleName;
    private Integer sort;
    private Integer status;
    private String remark;

    /**
     * 角色对应的菜单id集合
     */
    private List<Long> menuList;
}
