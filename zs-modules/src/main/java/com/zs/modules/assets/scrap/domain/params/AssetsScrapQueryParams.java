package com.zs.modules.assets.scrap.domain.params;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssetsScrapQueryParams extends BasePageParams {

    private String serialNo;
}
