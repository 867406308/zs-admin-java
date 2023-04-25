package com.zs.modules.sys.user.domain.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class SysUserDTO {

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
