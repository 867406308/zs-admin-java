package com.zs.assets.scrap.domain.params;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssetsScrapDetailsQueryParams extends BasePageParams {

    private Long scrapId;

    private String excelName;
}
