package com.zs.assets.inventory.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 86740
 */
@Data
@TableName("assets_inventory_details")
public class AssetsInventoryDetailsEntity {

    @TableId
    private Long id;

    /**
     * 主表id
     */
    private Long inventoryId;
    /**
     * 资产名称
     */
    @TableField(exist = false)
    private String name;
    /**
     * 型号
     */
    @TableField(exist = false)
    private String spec;
    /**
     * 单位
     */
    @TableField(exist = false)
    private String unit;
    /**
     * 采购单价
     */
    @TableField(exist = false)
    private BigDecimal buyPrice;
    /**
     * 使用人
     */
    @TableField(exist = false)
    private String useUserName;
    /**
     * 资产存放位置
     */
    @TableField(exist = false)
    private String storageLocation;

    /**
     * 资产编号
     */
    private String assetsSerialNo;

    /**
     * 资产账面状态
     */
    private String assetsStatusCode;
    /**
     * 盘点后结果状态
     */
    private String assetsResultStatusCode;
}
