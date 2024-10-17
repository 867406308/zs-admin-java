package com.zs.modules.assets.scrap.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 86740
 */
@Data
public class AssetsScrapDetailsVo {

    private String assetsSerialNo;
    private String assetsName;
    private BigDecimal buyPrice;
    private String unit;
    private String spec;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;
    private Long manageOrgId;
    private String manageOrgName;
    private Long manageUserId;
    private String manageUserName;
    private BigDecimal depreciatedPrice;
}
