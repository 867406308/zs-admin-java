package com.zs.modules.sys.menu.domain.dto;

import lombok.Data;

@Data
public class SysMenuDTO {


    private Long sysMenuId;

    private Long pid;

    private String path;

    private String name;

    private Integer type;

    private String component;

    private String title;

    private String icon;

    private Integer sort;
    private String permissions;
}
