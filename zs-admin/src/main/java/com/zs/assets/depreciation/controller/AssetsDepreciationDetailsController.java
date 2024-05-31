package com.zs.assets.depreciation.controller;

import com.zs.assets.depreciation.domain.params.AssetsDepreciationAddParams;
import com.zs.assets.depreciation.domain.params.AssetsDepreciationDetailsQueryParams;
import com.zs.assets.depreciation.domain.params.AssetsDepreciationQueryParams;
import com.zs.assets.depreciation.domain.vo.AssetsDepreciationDetailsVo;
import com.zs.assets.depreciation.domain.vo.AssetsDepreciationVo;
import com.zs.assets.depreciation.service.IAssetsDepreciationDetailsService;
import com.zs.assets.depreciation.service.IAssetsDepreciationService;
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
@RequestMapping("fixedAssets/assets/depreciation/details")
public class AssetsDepreciationDetailsController {

    @Resource
    private IAssetsDepreciationDetailsService iAssetsDepreciationDetailsService;

    @GetMapping("page")
    @PreAuthorize("hasAuthority('assets:depreciation:page')")
    public Result<PageResult<AssetsDepreciationDetailsVo>> page(AssetsDepreciationDetailsQueryParams assetsDepreciationDetailsQueryParams) {
        PageResult<AssetsDepreciationDetailsVo> page = iAssetsDepreciationDetailsService.page(assetsDepreciationDetailsQueryParams);
        return new Result<PageResult<AssetsDepreciationDetailsVo>>().ok(page);
    }
}
