package com.zs.assets.depreciation.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资产折旧vo
 *
 * @author 86740
 */
@Data
public class AssetsDepreciationVo {

    private Long id;
    private String name;
    private Integer amount;
    private BigDecimal amountPrice;
    private Date createDate;
}
