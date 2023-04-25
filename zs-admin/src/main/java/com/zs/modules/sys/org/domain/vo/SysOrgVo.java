package com.zs.modules.sys.org.domain.vo;

import com.zs.common.utils.TreeNode;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysOrgVo extends TreeNode<SysOrgVo> implements Serializable {

    private Long sysOrgId;

    private Long pid;
    private String sysOrgName;

    public void setSysOrgId(Long sysOrgId) {
        this.sysOrgId = sysOrgId;
        this.setId(sysOrgId);
    }
}
