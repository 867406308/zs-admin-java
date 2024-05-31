package com.zs.sys.dept.domain.vo;

import com.zs.common.core.utils.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDeptUserVo extends TreeNode<SysDeptUserVo> implements Serializable {

    private Long id;
    private Long pid;
    private String name;
    private List<SysDeptUserVo> children = new ArrayList<>();
}
