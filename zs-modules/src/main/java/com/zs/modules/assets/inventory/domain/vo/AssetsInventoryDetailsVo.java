package com.zs.modules.assets.inventory.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 86740
 */
@Data
public class AssetsInventoryDetailsVo implements Serializable {

    /**
     * 资产编号
     */
    private String assetsSerialNo;
    /**
     * 名称
     */
    private String name;
    /**
     * 型号
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;
    /**
     * 采购单价
     */
    private BigDecimal buyPrice;
    /**
     * 使用人
     */
    private String useUserName;
    /**
     * 应折旧月数
     */
    private Integer depreciationMonths;
    /**
     * 资产存放位置
     */
    private String storageLocation;
    /**
     * 资产账面状态字典
     */
    private String assetsStatusCode;
    /**
     * 资产账面状态值
     */
    private String assetsStatusLabel;
    /**
     * 盘点后结果状态字典
     */
    private String assetsResultStatusCode;
    /**
     * 盘点后结果状态值
     */
    private String assetsResultStatusCodeLabel;
}
