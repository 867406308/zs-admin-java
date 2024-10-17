package com.zs.modules.sys.post.domain.excel;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
@ExcelIgnoreUnannotated
public class SysPostExcel {

    @ExcelProperty("所属部门")
    private String deptName;
    @ExcelProperty("岗位名称")
    private String postName;
}
