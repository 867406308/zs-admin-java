package com.zs.modules.sys.notice.controller;


import com.zs.common.aop.annotation.Log;
import com.zs.common.core.core.Result;
import com.zs.common.core.enums.OperationTypeEnum;
import com.zs.common.core.page.PageResult;
import com.zs.modules.sys.notice.domain.params.SysNoticeAddParams;
import com.zs.modules.sys.notice.domain.params.SysNoticeQueryParams;
import com.zs.modules.sys.notice.domain.params.SysNoticeUpdateParams;
import com.zs.modules.sys.notice.domain.vo.SysNoticeVo;
import com.zs.modules.sys.notice.service.SysNoticeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 86740
 */
@RestController
@RequestMapping("system/sys/notice")
@Tag(name = "通知公告")
public class SysNoticeController {

    @Resource
    private SysNoticeService sysNoticeService;


    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:notice:page')")
    public Result<PageResult<SysNoticeVo>> page(SysNoticeQueryParams sysNoticeQueryParams) {
        PageResult<SysNoticeVo> page = sysNoticeService.page(sysNoticeQueryParams);
        return new Result<PageResult<SysNoticeVo>>().ok(page);
    }

    @Log(module = "通知公告-新增", type = OperationTypeEnum.ADD, description = "新增通知公告信息")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('sys:notice:save')")
    public Result<?> save(@RequestBody  SysNoticeAddParams sysNoticeAddParams) {
        sysNoticeService.save(sysNoticeAddParams);
        return new Result<>().ok();
    }

    @Log(module = "通知公告-草稿修改", type = OperationTypeEnum.UPDATE, description = "修改通知公告草稿信息")
    @PutMapping("update")
    @PreAuthorize("hasAuthority('sys:notice:update')")
    public Result<?> update(@RequestBody SysNoticeUpdateParams sysNoticeUpdateParams) {
        sysNoticeService.update(sysNoticeUpdateParams);
        return new Result<>().ok();
    }

    @Log(module = "通知公告-删除", type = OperationTypeEnum.DELETE, description = "删除通知公告信息")
    @DeleteMapping("{sysNoticeId}")
    @PreAuthorize("hasAuthority('sys:notice:delete')")
    public Result<?> delete(@PathVariable Long sysNoticeId) {
        sysNoticeService.delete(sysNoticeId);
        return new Result<>().ok();
    }


    @GetMapping("{sysNoticeId}")
    @PreAuthorize("hasAuthority('sys:notice:info')")
    public Result<SysNoticeVo> get(@PathVariable Long sysNoticeId) {
        SysNoticeVo sysNoticeVo = sysNoticeService.get(sysNoticeId);
        return new Result<SysNoticeVo>().ok(sysNoticeVo);
    }


//    @Log(module = "通知公告-发布", type = OperationTypeEnum.UPDATE, description = "发布通知公告信息")
//    @PutMapping("release/{sysNoticeId}")
//    @PreAuthorize("hasAuthority('sys:notice:release')")
//    public Result<?> release(@PathVariable Long sysNoticeId) {
//        sysNoticeService.release(sysNoticeId);
//        return new Result<>().ok();
//    }


    @GetMapping("/limit/{num}")
//    @PreAuthorize("hasAuthority('sys:notice:info')")
    public Result<List<SysNoticeVo>>  getLimit(@PathVariable Integer num) {
        List<SysNoticeVo> sysNoticeVoList = sysNoticeService.getLimit(num);
        return new Result<List<SysNoticeVo>>().ok(sysNoticeVoList);
    }

}
