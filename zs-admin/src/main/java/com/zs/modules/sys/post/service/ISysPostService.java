package com.zs.modules.sys.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.post.domain.entity.SysPostEntity;
import com.zs.modules.sys.post.domain.query.SysPostAddParams;
import com.zs.modules.sys.post.domain.query.SysPostQueryParams;
import com.zs.modules.sys.post.domain.vo.SysPostVo;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysPostService extends IService<SysPostEntity> {

    PageResult<SysPostVo> page(SysPostQueryParams sysPostQueryParams);

    List<SysPostVo> getList();

    void save(SysPostAddParams sysPostAddParams);

    void update(SysPostAddParams sysPostAddParams);

    SysPostVo getById(Long id);
}
