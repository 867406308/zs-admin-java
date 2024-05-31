package com.zs.assets.allot.controller;

import com.zs.assets.allot.domain.params.AssetsAllotQueryParams;
import com.zs.assets.allot.domain.vo.AssetsAllotVo;
import com.zs.assets.allot.service.AssetsAllotService;
import com.zs.common.core.core.Result;
import com.zs.common.core.page.PageResult;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资产调拨
 *
 * @author 86740
 */
@RestController
@RequestMapping("fixedAssets/assets/allot")
public class AssetsAllotController {
    @Resource
    private AssetsAllotService assetsAllotService;


    @GetMapping("page")
    @PreAuthorize("hasAuthority('assets:allot:page')")
    public Result<PageResult<AssetsAllotVo>> page(AssetsAllotQueryParams assetsAllotQueryParams) {
        PageResult<AssetsAllotVo> page = assetsAllotService.page(assetsAllotQueryParams);
        return new Result<PageResult<AssetsAllotVo>>().ok(page);
    }
}
