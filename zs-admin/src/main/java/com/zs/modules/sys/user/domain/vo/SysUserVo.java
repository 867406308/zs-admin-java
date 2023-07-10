package com.zs.modules.sys.user.domain.vo;

import lombok.Data;

/**
 * @author 86740
 */
@Data
public class SysUserVo {

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
