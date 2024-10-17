package com.zs.modules.assets.classify.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 86740
 */
@Data
@TableName("assets_classify_school")
public class AssetsClassifySchoolEntity {

    @TableId
    private Long id;
    private String name;
    private Integer depreciationYears;
    private Long pid;
    private String pids;
    private Long gbcId;
    private String remark;
    private Integer isDepreciation;

    @TableField(exist = false)
    private String gbName;
}
