package com.zs.common.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;


/**
 * @author 86740
 */
@Data
public class SysUser {

    private Long sysUserId;
    private String username;

    @JsonIgnore
    private String password;
    private String realName;
    private String avatar;
    private String phone;
    private String email;
    private Integer age;
    private Integer sex;
    private Integer isAdmin;
    private String ip;
    private String ipAddress;
    private Date lastLoginTime;
    private Integer status;

    private Long sysDeptId;
    private Long sysPostId;
}
