package com.zs.sys.post.domain.params;

import lombok.Data;

/**
 * @author 86740
 */
@Data
public class SysPostAddParams {

    private Long sysPostId;
    private String postName;
    private Long sysDeptId;
    private Integer sort;
    private Integer status;
    private String remark;
}
