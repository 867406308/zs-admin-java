package com.zs.assets.depreciation.domain.params;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资产折旧修改参数
 *
 * @author 86740
 */
@Data
public class AssetsDepreciationUpdateParams {

    private Long id;
    private Integer name;
    private Integer amount;
    private BigDecimal amountPrice;
    private Date createDate;
}
