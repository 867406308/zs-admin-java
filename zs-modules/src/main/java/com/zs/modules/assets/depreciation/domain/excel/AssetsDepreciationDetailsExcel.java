package com.zs.modules.assets.depreciation.domain.excel;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
@ExcelIgnoreUnannotated
public class AssetsDepreciationDetailsExcel {

    @ExcelProperty("资产编号")
    private String assetsSerialNo;

    @ExcelProperty("资产大类")
    private String topClassifyName;

    @ExcelProperty("资产名称")
    private String assetsName;


//    /**
//     * 累计折旧金额
//     **/
//    @ExcelProperty("计算折旧/摊销额（元）")
//    private BigDecimal accumulatedDepreciation;
//
//    /**
//     * 当月计提折旧金额
//     **/
//    @ExcelProperty("本次折旧/摊销额（元）")
//    private BigDecimal amount;
//
//    @ExcelProperty("最大可折旧金额（元）")
//    private BigDecimal originalPrice;
//
//    @ExcelProperty("上期累计折旧/摊销（元）")
//    private String assetsName;
//
//    @ExcelProperty("价值")
//    private BigDecimal originalPrice;
//
//    @ExcelProperty("本次折旧/摊销月数")
//    private String assetsName;
//
//    @ExcelProperty("上期已计提折旧/摊销月数")
//    private String assetsName;
//
//    /**
//     * 累计折旧月数
//     **/
//    @ExcelProperty("折旧/摊销年限（月）")
//    private Integer accumulatedDepreciationMonths;
//
//    @ExcelProperty("原资产编号")
//    private String assetsName;
//
//    @ExcelProperty("财务入账日期")
//    private Date entryDate;
}
