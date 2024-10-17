package com.zs.modules.sys.menu.domain.vo;


import com.zs.common.core.utils.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
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

    @SuppressWarnings("unused")
    public Long getSysMenuId() {
        return sysMenuId;
    }

    @SuppressWarnings("unused")
    public void setSysMenuId(Long sysMenuId) {
        this.sysMenuId = sysMenuId;
        this.setId(sysMenuId);
    }
}
