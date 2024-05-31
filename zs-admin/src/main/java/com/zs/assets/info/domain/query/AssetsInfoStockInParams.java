package com.zs.assets.info.domain.query;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 86740
 * 资产入库参数
 */
@Data
public class AssetsInfoStockInParams implements Serializable {

    /**
     * 会计凭证号
     **/
    private String accountingVoucher;
    /**
     * 入账日期
     **/
    private Date entryDate;
    /**
     * 项目代码
     **/
    private String projectCode;
    /**
     * 发票号
     **/
    private String invoiceNumber;
    /**
     * 待入库资产列表
     **/
    private List<AssetsInfoAddParams> assetsInfoList;
}
