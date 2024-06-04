package com.zs.assets.allot.controller;

import com.zs.assets.allot.domain.params.AssetsAllotQueryParams;
import com.zs.assets.allot.domain.vo.AssetsAllotDetailsVo;
import com.zs.assets.allot.domain.vo.AssetsAllotVo;
import com.zs.assets.allot.service.AssetsAllotDetailsService;
import com.zs.assets.allot.service.AssetsAllotService;
import com.zs.common.core.core.Result;
import com.zs.common.core.page.PageResult;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Resource
    private AssetsAllotDetailsService assetsAllotDetailsService;


    @GetMapping("page")
    @PreAuthorize("hasAuthority('assets:allot:page')")
    public Result<PageResult<AssetsAllotVo>> page(AssetsAllotQueryParams assetsAllotQueryParams) {
        PageResult<AssetsAllotVo> page = assetsAllotService.page(assetsAllotQueryParams);
        return new Result<PageResult<AssetsAllotVo>>().ok(page);
    }

    @GetMapping("{allotId}")
    @PreAuthorize("hasAuthority('assets:allot:info')")
    public Result<List<AssetsAllotDetailsVo>> page(@PathVariable("allotId") Long allotId) {
        List<AssetsAllotDetailsVo> list = assetsAllotDetailsService.getAllotDetails(allotId);
        return new Result<List<AssetsAllotDetailsVo>>().ok(list);
    }
}
