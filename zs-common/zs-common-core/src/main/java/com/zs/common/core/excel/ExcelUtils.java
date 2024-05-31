package com.zs.common.core.excel;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson2.JSON;
import com.zs.common.core.core.Result;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * @author 86740
 */
public class ExcelUtils {


    public static void exportExcel(HttpServletResponse response, String fileName, Class<?> clazz, Collection<?> list) throws IOException {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + ".xlsx");
            EasyExcel.write(response.getOutputStream(), clazz).autoCloseStream(false).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).registerConverter(new LongStringConverter()).sheet("").doWrite(list);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Result<?> result = new Result<>().error("下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(result));
        }

    }
}
