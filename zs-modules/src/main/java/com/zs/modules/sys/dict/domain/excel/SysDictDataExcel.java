package com.zs.modules.sys.dict.domain.excel;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
@ExcelIgnoreUnannotated
public class SysDictDataExcel {

    @ExcelProperty("字典类型")
    private String dictType;
    @ExcelProperty("字典标签")
    private String dictLabel;
    @ExcelProperty("字典值")
    private String dictValue;
}
