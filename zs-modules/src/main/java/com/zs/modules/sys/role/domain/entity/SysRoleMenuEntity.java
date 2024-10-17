package com.zs.modules.sys.role.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 86740
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenuEntity {

    @TableId
    private Long sysRoleMenuId;

    private Long sysRoleId;

    private Long sysMenuId;
}
