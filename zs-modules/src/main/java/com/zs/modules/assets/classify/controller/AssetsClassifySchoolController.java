package com.zs.modules.assets.classify.controller;

import com.zs.modules.assets.classify.domain.query.AssetsClassifySchoolAddParams;
import com.zs.modules.assets.classify.domain.query.AssetsClassifySchoolQueryParams;
import com.zs.modules.assets.classify.domain.vo.AssetsClassifySchoolVo;
import com.zs.modules.assets.classify.service.IAssetsClassifySchoolService;
import com.zs.common.aop.annotation.Log;
import com.zs.common.core.core.Result;
import com.zs.common.core.enums.OperationTypeEnum;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 86740
 */
@RestController
@RequestMapping("fixedAssets/assets/classifySchool")
public class AssetsClassifySchoolController {

    @Resource
    private IAssetsClassifySchoolService iAssetsClassifySchoolService;


    @GetMapping("tree")
    @PreAuthorize("hasAuthority('assets:classifySchool:tree')")
    public Result<List<AssetsClassifySchoolVo>> list(AssetsClassifySchoolQueryParams assetsClassifySchoolQueryParams) {
        List<AssetsClassifySchoolVo> list = iAssetsClassifySchoolService.getList(assetsClassifySchoolQueryParams);
        return new Result<List<AssetsClassifySchoolVo>>().ok(list);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('assets:classifySchool:info')")
    public Result<AssetsClassifySchoolVo> get(@PathVariable("id") Long id) {
        AssetsClassifySchoolVo assetsClassifySchoolVo = iAssetsClassifySchoolService.get(id);
        return new Result<AssetsClassifySchoolVo>().ok(assetsClassifySchoolVo);
    }

    @PostMapping("save")
    @PreAuthorize("hasAuthority('assets:classifySchool:save')")
    public Result<?> save(@Valid @RequestBody AssetsClassifySchoolAddParams assetsClassifySchoolAddParams) {
        iAssetsClassifySchoolService.save(assetsClassifySchoolAddParams);
        return new Result<>().ok();
    }

    @PutMapping("update")
    @Log(module = "学校分类-修改", type = OperationTypeEnum.UPDATE, description = "修改学校分类")
    @PreAuthorize("hasAuthority('assets:classifySchool:update')")
    public Result<?> update(@Valid @RequestBody AssetsClassifySchoolAddParams assetsClassifySchoolAddParams) {
        iAssetsClassifySchoolService.update(assetsClassifySchoolAddParams);
        return new Result<>().ok();
    }

    @Log(module = "学校分类-删除", type = OperationTypeEnum.DELETE, description = "删除学校分类")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('assets:classifySchool:delete')")
    public Result<?> delete(@PathVariable("id") Long id) {
        iAssetsClassifySchoolService.delete(id);
        return new Result<>().ok();
    }

}
