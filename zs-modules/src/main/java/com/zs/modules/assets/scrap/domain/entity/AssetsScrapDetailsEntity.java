package com.zs.modules.assets.scrap.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资产报废明细
 *
 * @author 86740
 */
@Data
@TableName("assets_scrap_details")
public class AssetsScrapDetailsEntity implements Cloneable {

    @TableId
    private Long id;
    private Long scrapId;
    private String assetsSerialNo;

    @TableField(exist = false)
    private String assetsName;
    @TableField(exist = false)
    private BigDecimal buyPrice;
    @TableField(exist = false)
    private String unit;
    @TableField(exist = false)
    private String spec;
    @TableField(exist = false)
    private Date entryDate;
    @TableField(exist = false)
    private BigDecimal depreciatedPrice;
    @TableField(exist = false)
    private Long manageOrgId;
    @TableField(exist = false)
    private Long manageUserId;

    @Override
    public AssetsScrapDetailsEntity clone() {
        try {
            return (AssetsScrapDetailsEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
