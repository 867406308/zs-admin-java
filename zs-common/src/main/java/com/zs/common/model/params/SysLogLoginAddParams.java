package com.zs.common.model.params;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysLogLoginAddParams {


    private Long sysLogLoginId;
    private String loginTime;
    private String username;
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
