package com.zs.modules.sys.dict.domain.params;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 86740
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictTypeQueryParams extends BasePageParams {

    private String dictType;
    private String dictName;
    private Integer status;
}
