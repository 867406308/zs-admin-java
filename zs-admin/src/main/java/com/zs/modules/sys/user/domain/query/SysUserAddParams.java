package com.zs.modules.sys.user.domain.query;

import lombok.Data;

@Data
public class SysUserAddParams {

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
