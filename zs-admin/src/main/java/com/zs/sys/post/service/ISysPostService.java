package com.zs.sys.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.sys.post.domain.entity.SysPostEntity;
import com.zs.sys.post.domain.params.SysPostAddParams;
import com.zs.sys.post.domain.params.SysPostQueryParams;
import com.zs.sys.post.domain.vo.SysPostVo;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysPostService extends IService<SysPostEntity> {

    PageResult<SysPostVo> page(SysPostQueryParams sysPostQueryParams);

    List<SysPostVo> getList(SysPostQueryParams sysPostQueryParams);

    void save(SysPostAddParams sysPostAddParams);

    void update(SysPostAddParams sysPostAddParams);

    SysPostVo getById(Long id);
}
