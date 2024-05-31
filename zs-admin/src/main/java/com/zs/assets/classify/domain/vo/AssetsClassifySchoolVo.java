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
public class AssetsClassifySchoolVo extends TreeNode<AssetsClassifySchoolVo> implements Serializable {

    private Long id;
    private String name;
    private Integer depreciationYears;
    private Long pid;
    private String pids;
    private Long gbcId;
    private String gbName;
    private String remark;
    private Integer isDepreciation;

}
