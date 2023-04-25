package com.zs.modules.sys.org.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysOrgEntity {

    @TableId
    private Long sysOrgId;
    private Long pid;
    private String sysOrgName;
}
