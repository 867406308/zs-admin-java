package com.zs.sys.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.sys.post.domain.entity.SysPostEntity;
import com.zs.sys.post.domain.params.SysPostAddParams;
import com.zs.sys.post.domain.params.SysPostQueryParams;
import com.zs.sys.post.domain.vo.SysPostVo;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysPostService extends IService<SysPostEntity> {

    /** 分页 **/
    PageResult<SysPostVo> page(SysPostQueryParams sysPostQueryParams);

    /** 列表 **/
    @Nullable
    List<SysPostVo> getList(SysPostQueryParams sysPostQueryParams);

    /** 新增 **/
    void save(SysPostAddParams sysPostAddParams);

    /** 修改 **/
    void update(SysPostAddParams sysPostAddParams);

    /** 获取详情 **/
    SysPostVo getById(Long id);

    /** 批量删除 **/
    void batchDelById(Long[] sysPostIds);

    /** 删除 **/
    void delById(Long sysPostId);
}
