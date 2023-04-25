package com.zs.modules.sys.menu.domain.vo;

import com.zs.common.utils.TreeNode;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysMenuVo extends TreeNode<SysMenuVo> implements Serializable {


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

    public Long getSysMenuId() {
        return sysMenuId;
    }

    public void setSysMenuId(Long sysMenuId) {
        this.sysMenuId = sysMenuId;
        this.setId(sysMenuId);
    }
}
