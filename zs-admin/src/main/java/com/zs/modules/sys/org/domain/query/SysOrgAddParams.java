package com.zs.modules.sys.org.domain.query;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysOrgAddParams {

    @TableId
    private Long sysOrgId;

    private Long pid;
    private String sysOrgName;
}
