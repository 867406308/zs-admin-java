package com.zs.modules.assets.scrap.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 86740
 */
@Data
public class AssetsScrapVo {

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
    @JsonFormat(pattern = "yyyy-MM-dd")
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date approveDate;
    /**
     * 报废日期
     **/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date scrapDate;
}
