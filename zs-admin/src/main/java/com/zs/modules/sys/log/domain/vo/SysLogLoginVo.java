package com.zs.modules.sys.log.domain.vo;

import lombok.Data;

@Data
public class SysLogLoginVo {

    private Long sysLogLoginId;
    private String username;
    private String loginTime;
    private String ipAddress;
    private String city;
    private String userAgent;
    private Integer loginStatus;
    private String failureReason;
    private Integer loginMethod;
    private String loginSource;
    private String browser;
    private String os;
}
