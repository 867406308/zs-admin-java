package com.zs.modules.assets.inventory.domain.params;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 86740
 */
@Data
public class AssetsInventoryDetailsAddParams implements Serializable {

    /**
     * 主表id
     */
    private Long inventoryId;
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
    private String inventoryResult;
}
