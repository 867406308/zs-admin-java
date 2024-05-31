package com.zs.sys.log.domain.params;

import lombok.Data;

/**
 * @author 86740
 */
@Data
public class SysLogOperationAddParams {

    private Long sysLogOperationId;
    private String username;
    private String module;
    private String ipAddress;
    private String operationType;
    private String operationDescription;
    private String requestMethod;
    private String requestPath;
    private String requestParams;
    private Integer responseStatusCode;
    private String responseMessage;
    private Integer operationDuration;
    private String createTime;
}
