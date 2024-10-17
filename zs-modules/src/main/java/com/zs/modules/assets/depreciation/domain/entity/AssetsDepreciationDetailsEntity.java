package com.zs.modules.assets.depreciation.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资产折旧明细
 *
 * @author 86740
 */
@Data
@TableName("assets_depreciation_details")
public class AssetsDepreciationDetailsEntity {

    @TableId
    private Long id;
    private Long depreciationId;
    /**
     * 资产编号
     **/
    private String assetsSerialNo;
    /**
     * 管理部门ID
     **/
    private Long manageOrgId;
    /**
     * 使用部门ID
     **/
    private Long useOrgId;
    /**
     * 国标资产大类
     **/
    private String topLevelGbClassicCode;
    /**
     * 学校分类ID
     **/
    private Long schoolClassicId;
    /**
     * 资产原值
     **/
    private BigDecimal originalPrice;
    /**
     * 资产数量
     **/
    private Integer num;
    /**
     * 当月计提折旧金额
     **/
    private BigDecimal amount;
    /**
     * 累计折旧金额
     **/
    private BigDecimal accumulatedDepreciation;
    /**
     * 累计折旧月数
     **/
    private Integer accumulatedDepreciationMonths;
    /**
     * 折旧状态
     **/
    private String depreciationCode;
    /**
     * 是否当期折旧的资产
     **/
    private Integer isCurrentPeriod;


    @TableField(exist = false)
    private String assetsName;

    @TableField(exist = false)
    private String topClassifyName;

    @TableField(exist = false)
    private String classifyName;
}
