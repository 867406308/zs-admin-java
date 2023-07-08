package com.zs.modules.sys.dept.domain.vo;

import com.zs.common.utils.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptVo extends TreeNode<SysDeptVo> implements Serializable {

    private Long sysDeptId;

    private Long pid;
    private String deptName;
    private String remark;
    private Long deptHead;
    private Integer status;
    private Integer sort;

    public void setSysDeptId(Long sysDeptId) {
        this.sysDeptId = sysDeptId;
        this.setId(sysDeptId);
    }
}
