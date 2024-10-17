package com.zs.modules.assets.info.domain.excel;


import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 86740
 */
@Data
@ExcelIgnoreUnannotated
public class AssetsInfoExcel {




    @ExcelProperty("资产编号")
    private String serialNo;
    @ExcelProperty("入库单号")
    private String inNo;
    @ExcelProperty("资产名称")
    private String name;
    @ExcelProperty("资产分级")
    private String levelName;
    @ExcelProperty("资产大类")
    private String topClassifyName;
    @ExcelProperty("资产分类")
    private String classifyName;
    /**
     * 资产照片
     */
    private String pic;

    /**
     * 描述
     */
    private String description;
    @ExcelProperty("资产型号")
    private String spec;
    @ExcelProperty("单位")
    private String unit;
    @ExcelProperty("品牌商标")
    private String brand;
    @ExcelProperty("生产厂家")
    private String manufacturer;
    @ExcelProperty("出厂编号")
    private String factoryNo;
    @ExcelProperty("生产日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date productionDate;
    @ExcelProperty("质保日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date qualityDate;
//    @ExcelProperty("资产存放位置")
//    private String storageLocation;
    @ExcelProperty("存放位置")
    private String storageLocationDescription;


    @ExcelProperty("采购组织形式")
    private String formOfProcurementCodeDictLabel;
    @ExcelProperty("采购单价")
    @NumberFormat(value = "0.00")
    private BigDecimal buyPrice;
    @ExcelProperty("采购部门编码")
    private String buyOrgName;
    @ExcelProperty("采购人")
    private String buyUserName;
    @ExcelProperty("采购日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date buyDate;
    @ExcelProperty("验收部门")
    private String acceptOrgName;
    @ExcelProperty("验收人")
    private String acceptUserName;
    @ExcelProperty("验收日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date acceptDate;
    @ExcelProperty("验收单号")
    private String stockCode;
    @ExcelProperty("管理部门")
    private String manageOrgName;
    @ExcelProperty("管理人员")
    private String manageUserName;
    @ExcelProperty("使用部门")
    private String useOrgName;
    @ExcelProperty("使用人员")
    private String useUserName;
    @ExcelProperty("资产状态")
    private String assetsStatusDictLabel;
    @ExcelProperty("资产使用状态")
    private String useStatusDictLabel;
    @ExcelProperty("资产折旧状态")
    private String depreciationCodeDictLabel;



    @ExcelProperty("入账日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;
    @ExcelProperty("期初折旧金额")
    @NumberFormat(value = "0.00")
    private BigDecimal bopDepreciationPrice;
    @ExcelProperty("期初折旧月数")
    private Integer bopDepreciationMonths;

    @ExcelProperty("应折旧月数")
    private Integer depreciationMonths;
    @ExcelProperty("月均折旧额")
    @NumberFormat(value = "0.00")
    private BigDecimal monthlyDepreciationPrice;
    @ExcelProperty("已经折旧的月份")
    private Integer depreciatedMonths;
    @ExcelProperty("已经折旧的金额")
    @NumberFormat(value = "0.00")
    private BigDecimal depreciatedPrice;
    @ExcelProperty("当月折旧金额")
    @NumberFormat(value = "0.00")
    private BigDecimal currentPeriodDepreciatedPrice;

    @ExcelProperty("会计凭证号")
    private String accountingVoucher;
    @ExcelProperty("项目代码")
    private String projectCode;
    @ExcelProperty("发票号")
    private String invoiceNumber;
}
