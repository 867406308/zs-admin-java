package com.zs.sys.role.domain.vo;

import com.zs.common.core.model.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author 86740
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleVo extends BaseVo implements Serializable {

    private Long sysRoleId;
    private String roleName;
    private Integer sort;
    private Integer status;
    private String remark;

    private List<Long> menuList;
}
