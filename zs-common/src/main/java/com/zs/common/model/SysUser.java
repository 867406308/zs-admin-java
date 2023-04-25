package com.zs.common.model;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;


@Data
public class SysUser {

    private Long sysUserId;
    private String username;

    @JSONField(serialize = false)
    private String password;
    private String realName;
    private String avatar;
    private String phone;
    private String email;
    private Integer age;
    private Integer sex;
    private Integer isAdmin;
}
