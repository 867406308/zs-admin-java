package com.zs.assets.depreciation.domain.params;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资产折旧查询参数
 *
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssetsDepreciationQueryParams extends BasePageParams {

    private String name;
}
