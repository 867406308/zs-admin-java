package com.zs.assets.scrap.domain.excel;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 86740
 */
@Data
@ExcelIgnoreUnannotated
public class AssetsScrapDetailsExcel {

    @ExcelProperty("资产编号")
    private String assetsSerialNo;
    @ExcelProperty("资产名称")
    private String assetsName;
    @ExcelProperty("采购价格")
    @NumberFormat(value = "0.00")
    private BigDecimal buyPrice;
    @ExcelProperty("单位")
    private String unit;
    @ExcelProperty("规格型号")
    private String spec;
    @ExcelProperty("入库日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date entryDate;
    @ExcelProperty("管理部门")
    private String manageOrgName;
    @ExcelProperty("管理人")
    private String manageUserName;
    @ExcelProperty("累计折旧")
    @NumberFormat(value = "0.00")
    private BigDecimal depreciatedPrice;

}
