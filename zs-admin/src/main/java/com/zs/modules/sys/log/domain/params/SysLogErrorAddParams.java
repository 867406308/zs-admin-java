package com.zs.modules.sys.log.domain.params;

import lombok.Data;

/**
 * @author 86740
 */
@Data
public class SysLogErrorAddParams {

    private Long sysLogErrorId;
    private String username;
    private String module;
    private String ipAddress;
    private String exceptionType;
    private String exceptionMessage;
    private String requestMethod;
    private String requestPath;
    private String requestParams;
    private String createTime;
}
