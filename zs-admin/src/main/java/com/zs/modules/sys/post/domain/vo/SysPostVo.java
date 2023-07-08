package com.zs.modules.sys.post.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysPostVo implements Serializable {


    private Long sysPostId;
    private String postName;
    private Long sysDeptId;
    private Integer sort;
    private Integer status;
    private String remark;
}
