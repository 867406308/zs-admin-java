package com.zs.assets.allot.domain.params;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssetsAllotQueryParams extends BasePageParams {

    /**
     * 调拨单号
     **/
    private String serialNo;
}
