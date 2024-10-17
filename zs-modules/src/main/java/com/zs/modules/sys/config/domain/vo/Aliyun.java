package com.zs.modules.sys.config.domain.vo;

import lombok.Data;

/**
 *
 */
@Data
public class Aliyun {

    private String domain;
    private String prefix;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
