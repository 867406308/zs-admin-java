package com.zs.modules.sys.menu.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_menu")
public class SysMenuEntity {

    @TableId
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
