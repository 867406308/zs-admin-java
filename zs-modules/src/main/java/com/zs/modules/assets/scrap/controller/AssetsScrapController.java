package com.zs.modules.assets.scrap.controller;

import com.zs.common.core.core.Result;
import com.zs.common.core.page.PageResult;
import com.zs.modules.assets.scrap.domain.params.AssetsScrapAddParams;
import com.zs.modules.assets.scrap.domain.params.AssetsScrapQueryParams;
import com.zs.modules.assets.scrap.domain.vo.AssetsScrapVo;
import com.zs.modules.assets.scrap.service.AssetsScrapService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 资产报废
 *
 * @author 86740
 */
@RestController
@RequestMapping("fixedAssets/assets/scrap")
public class AssetsScrapController {


    @Resource
    private AssetsScrapService assetsScrapService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('assets:scrap:page')")
    public Result<PageResult<AssetsScrapVo>> page(AssetsScrapQueryParams assetsScrapQueryParams) {
        PageResult<AssetsScrapVo> page = assetsScrapService.page(assetsScrapQueryParams);
        return new Result<PageResult<AssetsScrapVo>>().ok(page);
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('assets:scrap:save')")
    public Result<?> oneKeyDepreciation(@RequestBody AssetsScrapAddParams assetsScrapAddParams) {
        assetsScrapService.save(assetsScrapAddParams);
        return new Result<>().ok();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('assets:scrap:info')")
    public Result<AssetsScrapVo> info(@PathVariable("id") Long id) {
        AssetsScrapVo assetsScrapVo = assetsScrapService.getById(id);
        return new Result<AssetsScrapVo>().ok(assetsScrapVo);
    }


}
