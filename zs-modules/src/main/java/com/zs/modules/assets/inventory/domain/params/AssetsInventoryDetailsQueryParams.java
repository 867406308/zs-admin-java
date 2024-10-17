package com.zs.modules.assets.inventory.domain.params;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssetsInventoryDetailsQueryParams extends BasePageParams {

    private Long inventoryId;
}
