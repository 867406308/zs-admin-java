package com.zs.common.core.model;


import lombok.Data;

/**
 * @author 86740
 */
@Data
public class BaseVo {
    private Long creator;
    private String createTime;
    private Long updater;
    private String updateTime;
}
