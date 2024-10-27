package com.zs.modules.assets.inventory.controller;

import com.zs.common.core.core.Result;
import com.zs.common.core.page.PageResult;
import com.zs.modules.assets.inventory.domain.params.AssetsInventoryDetailsQueryParams;
import com.zs.modules.assets.inventory.domain.vo.AssetsInventoryDetailsVo;
import com.zs.modules.assets.inventory.service.AssetsInventoryDetailsService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 86740
 */
@RestController
@RequestMapping("fixedAssets/assets/inventory/details")
public class AssetsInventoryDetailsController {

    @Resource
    private AssetsInventoryDetailsService assetsInventoryDetailsService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('assets:inventory:page')")
    public Result<PageResult<AssetsInventoryDetailsVo>> page(AssetsInventoryDetailsQueryParams assetsInventoryDetailsQueryParams) {
        PageResult<AssetsInventoryDetailsVo> page = assetsInventoryDetailsService.page(assetsInventoryDetailsQueryParams);
        return new Result<PageResult<AssetsInventoryDetailsVo>>().ok(page);
    }
}
