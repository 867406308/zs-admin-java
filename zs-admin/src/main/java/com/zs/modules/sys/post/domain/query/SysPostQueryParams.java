package com.zs.modules.sys.post.domain.query;

import com.zs.common.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysPostQueryParams extends BasePageParams implements Serializable{

    private String postName;
    private String sysDeptId;
    private Integer status;
}
