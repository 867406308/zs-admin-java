package com.zs.modules.sys.dept.domain.params;

import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 86740
 */
@Data
public class SysDeptAddParams {

    @TableId
    private Long sysDeptId;
    @NotNull(message = "上级部门id不能为空")
    private Long pid;
    @NotBlank(message = "部门名称不能为空")
    private String deptName;
    private Long sysUserId;
    private String describe;
    private Integer status;
    private Integer sort;
}
