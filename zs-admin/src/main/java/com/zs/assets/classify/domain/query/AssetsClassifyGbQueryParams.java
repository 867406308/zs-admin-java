package com.zs.assets.classify.domain.query;

import com.zs.common.core.page.BasePageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 86740
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssetsClassifyGbQueryParams extends BasePageParams {

    private String name;
    private String code;
}
