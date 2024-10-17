package com.zs.modules.sys.log.domain.excel;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 86740
 */
@Data
@ExcelIgnoreUnannotated
public class SysLogErrorExcel {

    @ExcelProperty("登录用户名")
    private String username;

    @ExcelProperty("所属模块")
    private String module;

    @ExcelProperty("请求IP地址")
    private String ipAddress;

    @ExcelProperty("异常类型")
    private String exceptionType;

    @ExcelProperty("异常信息")
    private String exceptionMessage;

    @ExcelProperty("请求方法")
    private String requestMethod;

    @ExcelProperty("请求路径")
    private String requestPath;

    @ExcelProperty("请求参数")
    private String requestParams;

    @ExcelProperty("创建时间")
    private String createTime;
}
