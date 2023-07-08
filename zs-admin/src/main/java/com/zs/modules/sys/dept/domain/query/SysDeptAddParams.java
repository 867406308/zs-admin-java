package com.zs.modules.sys.dept.domain.query;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class SysDeptAddParams {

    @TableId
    private Long sysDeptId;
    @NotBlank(message = "上级部门id不能为空")
    private Long pid;
    @NotBlank(message = "部门名称不能为空")
    private String deptName;
    private String describe;
    private Integer status;
    private Integer sort;
}
