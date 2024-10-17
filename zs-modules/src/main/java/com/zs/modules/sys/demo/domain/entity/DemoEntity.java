package com.zs.modules.sys.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zs.common.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sys_dept")
@EqualsAndHashCode(callSuper = true)
public class DemoEntity extends BaseEntity {

    @TableId
    private Long id;
    private String name;
    @TableField(fill = FieldFill.INSERT)
    private Long createDeptId;
}
