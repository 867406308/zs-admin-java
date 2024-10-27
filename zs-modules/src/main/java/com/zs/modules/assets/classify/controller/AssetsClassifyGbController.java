package com.zs.modules.assets.classify.controller;

import com.zs.common.aop.annotation.Log;
import com.zs.common.core.core.Result;
import com.zs.common.core.enums.OperationTypeEnum;
import com.zs.modules.assets.classify.domain.query.AssetsClassifyGbAddParams;
import com.zs.modules.assets.classify.domain.query.AssetsClassifyGbQueryParams;
import com.zs.modules.assets.classify.domain.vo.AssetsClassifyGbVo;
import com.zs.modules.assets.classify.service.IAssetsClassifyGbService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 86740
 */
@RestController
@RequestMapping("fixedAssets/assets/classifyGb")
public class AssetsClassifyGbController {

    @Resource
    private IAssetsClassifyGbService iAssetsClassifyGbService;


    @GetMapping("tree")
    @PreAuthorize("hasAuthority('assets:classifyGb:tree')")
    public Result<List<AssetsClassifyGbVo>> list(AssetsClassifyGbQueryParams assetsClassifyGbQueryParams) {
        List<AssetsClassifyGbVo> list = iAssetsClassifyGbService.getList(assetsClassifyGbQueryParams);
        return new Result<List<AssetsClassifyGbVo>>().ok(list);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('assets:classifyGb:info')")
    public Result<AssetsClassifyGbVo> get(@PathVariable("id") Long id) {
        AssetsClassifyGbVo assetsClassifyGbVo = iAssetsClassifyGbService.get(id);
        return new Result<AssetsClassifyGbVo>().ok(assetsClassifyGbVo);
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('assets:classifyGb:save')")
    public Result<?> save(@Valid @RequestBody AssetsClassifyGbAddParams assetsClassifyGbAddParams) {
        iAssetsClassifyGbService.save(assetsClassifyGbAddParams);
        return new Result<>().ok();
    }

    @PutMapping("update")
    @Log(module = "国标分类-修改", type = OperationTypeEnum.UPDATE, description = "修改国标分类")
    @PreAuthorize("hasAuthority('assets:classifyGb:update')")
    public Result<?> update(@Valid @RequestBody AssetsClassifyGbAddParams assetsClassifyGbAddParams) {
        iAssetsClassifyGbService.update(assetsClassifyGbAddParams);
        return new Result<>().ok();
    }

    @Log(module = "国标分类-删除", type = OperationTypeEnum.DELETE, description = "删除国标分类")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('assets:classifyGb:delete')")
    public Result<?> delete(@PathVariable("id") Long id) {
        iAssetsClassifyGbService.delete(id);
        return new Result<>().ok();
    }

}
