package com.zs.modules.sys.post.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 86740
 */
@Data
public class SysPostVo implements Serializable {


    private Long sysPostId;
    private String postName;
    private Long sysDeptId;
    private Integer sort;
    private Integer status;
    private String remark;
}
