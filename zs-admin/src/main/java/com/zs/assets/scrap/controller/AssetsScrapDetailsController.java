package com.zs.assets.scrap.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zs.assets.scrap.domain.excel.AssetsScrapDetailsExcel;
import com.zs.assets.scrap.domain.params.AssetsScrapDetailsQueryParams;
import com.zs.assets.scrap.domain.vo.AssetsScrapDetailsVo;
import com.zs.assets.scrap.service.AssetsScrapDetailsService;
import com.zs.common.core.core.Result;
import com.zs.common.core.excel.ExcelUtils;
import com.zs.common.core.page.PageResult;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author 86740
 */
@RestController
@RequestMapping("fixedAssets/assets/scrap/details")
public class AssetsScrapDetailsController {

    @Resource
    private AssetsScrapDetailsService assetsScrapDetailsService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('assets:scrap:page')")
    public Result<PageResult<AssetsScrapDetailsVo>> page(AssetsScrapDetailsQueryParams assetsScrapDetailsQueryParams) {
        PageResult<AssetsScrapDetailsVo> page = assetsScrapDetailsService.page(assetsScrapDetailsQueryParams);
        return new Result<PageResult<AssetsScrapDetailsVo>>().ok(page);
    }

    @GetMapping("export")
    @PreAuthorize("hasAuthority('assets:scrap:export')")
    public void export(HttpServletResponse response, @NotNull AssetsScrapDetailsQueryParams assetsScrapDetailsQueryParams) throws IOException {
        List<AssetsScrapDetailsVo> list = assetsScrapDetailsService.list(assetsScrapDetailsQueryParams);
        List<AssetsScrapDetailsExcel> excelList = BeanUtil.copyToList(list, AssetsScrapDetailsExcel.class);
        ExcelUtils.exportExcel(response, assetsScrapDetailsQueryParams.getExcelName(), AssetsScrapDetailsExcel.class, excelList);
    }

}
