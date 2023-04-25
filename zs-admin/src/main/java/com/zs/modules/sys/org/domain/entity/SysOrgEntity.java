package com.zs.modules.sys.org.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_org")
public class SysOrgEntity {

    @TableId
    private Long sysOrgId;
    private Long pid;
    private String sysOrgName;
}
