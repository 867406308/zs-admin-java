package com.zs.assets.classify.domain.vo;

import com.zs.common.core.utils.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssetsClassifyGbVo extends TreeNode<AssetsClassifyGbVo> implements Serializable {

    private Long id;
    private String code;
    private String name;
    private Long pid;
    private String remark;

}
