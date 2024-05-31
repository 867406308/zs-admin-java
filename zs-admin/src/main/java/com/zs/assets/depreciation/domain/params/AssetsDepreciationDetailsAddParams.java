package com.zs.assets.depreciation.domain.params;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 资产折旧明细添加参数
 *
 * @author 86740
 */
@Data
public class AssetsDepreciationDetailsAddParams {

    /**
     * 资产编号
     **/
    private String assetsSerialNo;
    /**
     * 当月折旧金额
     **/
    private BigDecimal amount;
    /**
     * 资产原值
     **/
    private BigDecimal buyPrice;
    /**
     * 累计折旧金额
     **/
    private BigDecimal accumulatedDepreciation;
    /**
     * 累计折旧月数
     **/
    private Integer accumulatedDepreciationMonths;
}
