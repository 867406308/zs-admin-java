package com.zs.modules.sys.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zs.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUserEntity extends BaseEntity {

    @TableId
    private Long sysUserId;
    private String username;
    private String password;
    private String realName;
    private String phone;
    private String email;
    private Integer age;
    private Integer sex;
    private Integer isAdmin;
}
