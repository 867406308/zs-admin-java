package com.zs.modules.assets.depreciation.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资产折旧
 *
 * @author 86740
 */
@Data
@TableName("assets_depreciation")
public class AssetsDepreciationEntity {

    @TableId
    private Long id;
    private String name;
    private Integer amount;
    private BigDecimal amountPrice;
    private Date createDate;

}
