package com.zs.sys.post.domain.params;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysPostQueryParams extends BasePageParams implements Serializable {

    private String postName;
    private Long sysDeptId;
    private Integer status;
}
