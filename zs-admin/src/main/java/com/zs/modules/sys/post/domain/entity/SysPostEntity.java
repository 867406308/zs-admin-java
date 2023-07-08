package com.zs.modules.sys.post.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zs.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


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