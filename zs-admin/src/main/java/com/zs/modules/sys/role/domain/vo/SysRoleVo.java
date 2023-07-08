package com.zs.modules.sys.role.domain.vo;

import com.zs.common.model.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleVo extends BaseVo implements Serializable {

    private Long sysRoleId;
    private String roleName;
    private Integer sort;
    private Integer status;
    private String remark;
}
