package com.zs.common.model;


import lombok.Data;

@Data
public class BaseVo {
    private Long creator;
    private String createTime;
    private Long updater;
    private String updateTime;
}
