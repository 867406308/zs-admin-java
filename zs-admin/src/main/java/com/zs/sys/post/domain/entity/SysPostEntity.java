package com.zs.sys.post.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zs.common.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author 86740
 */
@Data
@TableName("sys_post")
@EqualsAndHashCode(callSuper = false)
public class SysPostEntity extends BaseEntity {

    @TableId
    private Long sysPostId;
    private String postName;
    private Long sysDeptId;
    private Integer sort;
    private Integer status;
    private String remark;

}
