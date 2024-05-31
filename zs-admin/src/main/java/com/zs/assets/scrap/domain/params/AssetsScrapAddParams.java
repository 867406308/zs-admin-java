package com.zs.assets.scrap.domain.params;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 86740
 */
@Data
public class AssetsScrapAddParams {

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
     * 资产编号集合
     **/
    private List<String> serialNoList;
}
