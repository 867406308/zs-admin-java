package com.zs.modules.sys.menu.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zs.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@TableName("sys_menu")
@EqualsAndHashCode(callSuper = true)
public class SysMenuEntity extends BaseEntity {

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
