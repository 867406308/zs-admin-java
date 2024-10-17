package com.zs.modules.assets.allot.controller;

import com.zs.modules.assets.allot.domain.params.AssetsAllotAddParams;
import com.zs.modules.assets.allot.domain.params.AssetsAllotQueryParams;
import com.zs.modules.assets.allot.domain.vo.AssetsAllotDetailsVo;
import com.zs.modules.assets.allot.domain.vo.AssetsAllotVo;
import com.zs.modules.assets.allot.service.AssetsAllotDetailsService;
import com.zs.modules.assets.allot.service.AssetsAllotService;
import com.zs.common.core.core.Result;
import com.zs.common.core.page.PageResult;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("save")
    @PreAuthorize("hasAuthority('assets:allot:save')")
    public Result<?> oneKeyDepreciation(@RequestBody AssetsAllotAddParams assetsAllotAddParams) {
        assetsAllotService.save(assetsAllotAddParams);
        return new Result<>().ok();
    }

}
