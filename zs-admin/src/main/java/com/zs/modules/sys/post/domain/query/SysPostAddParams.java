package com.zs.modules.sys.post.domain.query;

import lombok.Data;

@Data
public class SysPostAddParams {

    private Long sysPostId;
    private String postName;
    private Long sysDeptId;
    private Integer sort;
    private Integer status;
    private String remark;
}
