package com.zs.assets.scrap.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资产报废表
 *
 * @author 86740
 */
@Data
@TableName("assets_scrap")
public class AssetsScrapEntity {

    @TableId
    private Long id;
    /**
     * 报废单号
     **/
    private String serialNo;
    /**
     * 资产数量
     **/
    private Integer amount;
    /**
     * 总价
     **/
    private BigDecimal totalPrice;
    /**
     * 经办人
     **/
    private String applyUser;
    /**
     * 申请日期
     **/
    private Date applyDate;
    /**
     * 接收方
     **/
    private String receiver;
    /**
     * 批准文号
     **/
    private String approveSerialNo;
    /**
     * 批准部门
     **/
    private String approveOrg;
    /**
     * 批准文件
     **/
    private String approveFile;
    /**
     * 批准日期
     **/
    private Date approveDate;
    /**
     * 报废日期
     **/
    private Date scrapDate;

}
