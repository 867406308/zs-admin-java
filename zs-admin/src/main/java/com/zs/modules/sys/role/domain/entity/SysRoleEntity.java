package com.zs.modules.sys.role.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zs.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRoleEntity  extends BaseEntity {

    @TableId
    private Long sysRoleId;
    private String roleName;
    private Integer sort;
    private Integer status;
    private String remark;
}
