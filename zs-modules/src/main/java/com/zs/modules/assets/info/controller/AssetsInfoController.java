package com.zs.modules.assets.info.controller;

import com.zs.common.core.core.Result;
import com.zs.common.core.page.PageResult;
import com.zs.modules.assets.info.domain.query.AssetsInfoAddParams;
import com.zs.modules.assets.info.domain.query.AssetsInfoQueryParams;
import com.zs.modules.assets.info.domain.query.AssetsInfoSerialNoImportParams;
import com.zs.modules.assets.info.domain.query.AssetsInfoStockInParams;
import com.zs.modules.assets.info.domain.vo.AssetsInfoVo;
import com.zs.modules.assets.info.service.AssetsInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author 86740
 */
@RestController
@RequestMapping("fixedAssets/assets/info")
public class AssetsInfoController {

    @Resource
    private AssetsInfoService assetsInfoService;


    @GetMapping("page")
    @PreAuthorize("hasAuthority('assets:info:page')")
    public Result<PageResult<AssetsInfoVo>> page(AssetsInfoQueryParams assetsInfoQueryParams) {
        PageResult<AssetsInfoVo> page = assetsInfoService.page(assetsInfoQueryParams);
        return new Result<PageResult<AssetsInfoVo>>().ok(page);
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('assets:info:save')")
    public Result<?> save(@RequestBody AssetsInfoAddParams assetsInfoAddParams) {
        assetsInfoService.save(assetsInfoAddParams);
        return new Result<>().ok();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('assets:info:info')")
    public Result<AssetsInfoVo> get(@PathVariable("id") Long id) {
        AssetsInfoVo assetsInfoVo = assetsInfoService.getById(id);
        return new Result<AssetsInfoVo>().ok(assetsInfoVo);
    }

    @PostMapping("stockIn")
    @PreAuthorize("hasAuthority('assets:info:stockIn')")
    public Result<?> stockIn(@RequestBody AssetsInfoStockInParams assetsInfoStockInParams) {
        assetsInfoService.stockIn(assetsInfoStockInParams);
        return new Result<>().ok();
    }

    @PostMapping("getBySerialNoList")
    @PreAuthorize("hasAuthority('assets:info:list')")
    public Result<List<AssetsInfoVo>> getBySerialNoList(@RequestBody AssetsInfoSerialNoImportParams assetsInfoSerialNoImportParams) {
        return new Result<List<AssetsInfoVo>>().ok(assetsInfoService.getBySerialNoList(assetsInfoSerialNoImportParams));
    }

    @GetMapping("getTotalPrice")
    public Result<String> getTotalPrice() {
        return new Result<String>().ok(assetsInfoService.getTotalPrice());
    }

    @GetMapping("export")
    @PreAuthorize("hasAuthority('assets:info:export')")
    public void export(HttpServletResponse response, AssetsInfoQueryParams assetsInfoQueryParams) throws IOException {
        assetsInfoService.export(response, assetsInfoQueryParams);
    }

}
