package com.zs.modules.sys.config.domain.vo;

import lombok.Data;

/**
 * 文件配置VO
 */
@Data
public class SysConfigFileVo {

    private Integer type;
    private Local local;
    private Aliyun aliyun;
    private Tencent tencent;
    private Qiniu qiniu;


}
