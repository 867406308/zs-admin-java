package com.zs.sys.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 86740
 */
@Data
@TableName("sys_user_role")
public class SysUserRoleEntity {

    @TableId
    private Long sysUserRoleId;
    private Long sysUserId;
    private Long sysRoleId;
}
