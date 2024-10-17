package com.zs.modules.sys.config.domain.vo;


import lombok.Data;

/**
 */
@Data
public class SysConfigVo {

    private String configKey;
    private String configName;
    private String configValue;
    private Long creator;
    private String createTime;
    private Long updater;
    private String updateTime;
}
