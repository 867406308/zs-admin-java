package com.zs.modules.assets.depreciation.controller;

import com.zs.modules.assets.depreciation.domain.params.AssetsDepreciationAddParams;
import com.zs.modules.assets.depreciation.domain.params.AssetsDepreciationQueryParams;
import com.zs.modules.assets.depreciation.domain.vo.AssetsDepreciationVo;
import com.zs.modules.assets.depreciation.service.IAssetsDepreciationService;
import com.zs.common.aop.annotation.Log;
import com.zs.common.core.core.Result;
import com.zs.common.core.enums.OperationTypeEnum;
import com.zs.common.core.page.PageResult;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 资产折旧
 *
 * @author 86740
 */
@RestController
@RequestMapping("fixedAssets/assets/depreciation")
public class AssetsDepreciationController {

    @Resource
    private IAssetsDepreciationService iAssetsDepreciationService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('assets:depreciation:page')")
    public Result<PageResult<AssetsDepreciationVo>> page(AssetsDepreciationQueryParams assetsDepreciationQueryParams) {
        PageResult<AssetsDepreciationVo> page = iAssetsDepreciationService.page(assetsDepreciationQueryParams);
        return new Result<PageResult<AssetsDepreciationVo>>().ok(page);
    }

    @Log(module = "资产折旧-一键折旧", type = OperationTypeEnum.ADD, description = "一键折旧")
    @PostMapping("oneKeyDepreciation")
    @PreAuthorize("hasAuthority('assets:depreciation:oneKeyDepreciation')")
    public Result<?> oneKeyDepreciation(@RequestBody AssetsDepreciationAddParams assetsDepreciationAddParams) {
        iAssetsDepreciationService.oneKeyDepreciation(assetsDepreciationAddParams);
        return new Result<>().ok();
    }

}
