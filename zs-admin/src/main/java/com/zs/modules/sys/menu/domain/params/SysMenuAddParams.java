package com.zs.modules.sys.menu.domain.params;


import lombok.Data;

@Data
public class SysMenuAddParams{


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
