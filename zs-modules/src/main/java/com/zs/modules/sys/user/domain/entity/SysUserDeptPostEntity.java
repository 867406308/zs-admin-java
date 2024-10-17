package com.zs.modules.sys.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 86740
 */
@Data
@TableName("sys_user_dept_post")
public class SysUserDeptPostEntity {

    @TableId
    private Long sysUserDeptPostId;

    private Long sysUserId;

    private Long sysDeptId;

    private Long sysPostId;
}
