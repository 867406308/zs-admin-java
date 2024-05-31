package com.zs.assets.depreciation.domain.params;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资产折旧新增参数
 *
 * @author 86740
 */
@Data
public class AssetsDepreciationAddParams {

    private String name;
    private Integer amount;
    private BigDecimal amountPrice;
    private Date createDate;
}
