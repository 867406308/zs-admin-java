package com.zs.modules.assets.inventory.controller;

import com.zs.common.core.core.Result;
import com.zs.common.core.page.PageResult;
import com.zs.modules.assets.inventory.domain.params.AssetsInventoryAddParams;
import com.zs.modules.assets.inventory.domain.params.AssetsInventoryQueryParams;
import com.zs.modules.assets.inventory.domain.vo.AssetsInventoryVo;
import com.zs.modules.assets.inventory.service.AssetsInventoryService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 86740
 */
@RestController
@RequestMapping("fixedAssets/assets/inventory")
public class AssetsInventoryController {

    @Resource
    private AssetsInventoryService assetsInventoryService;


    @GetMapping("page")
    @PreAuthorize("hasAuthority('assets:inventory:page')")
    public Result<PageResult<AssetsInventoryVo>> page(AssetsInventoryQueryParams assetsInventoryQueryParams) {
        PageResult<AssetsInventoryVo> page = assetsInventoryService.page(assetsInventoryQueryParams);
        return new Result<PageResult<AssetsInventoryVo>>().ok(page);
    }

    @PostMapping("createInventoryTask")
    @PreAuthorize("hasAuthority('assets:inventory:createInventoryTask')")
    public Result<?> createInventoryTask(@RequestBody AssetsInventoryAddParams assetsInventoryAddParams) {
        assetsInventoryService.createInventoryTask(assetsInventoryAddParams);
        return new Result<>().ok();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('assets:inventory:info')")
    public Result<AssetsInventoryVo> get(@PathVariable("id") Long id) {
        AssetsInventoryVo assetsInventoryVo = assetsInventoryService.get(id);
        return new Result<AssetsInventoryVo>().ok(assetsInventoryVo);
    }

}
