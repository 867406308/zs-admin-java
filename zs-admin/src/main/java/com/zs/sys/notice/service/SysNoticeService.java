package com.zs.sys.notice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.sys.notice.domain.entity.SysNoticeEntity;
import com.zs.sys.notice.domain.params.SysNoticeAddParams;
import com.zs.sys.notice.domain.params.SysNoticeQueryParams;
import com.zs.sys.notice.domain.params.SysNoticeUpdateParams;
import com.zs.sys.notice.domain.vo.SysNoticeVo;

import java.util.List;

/**
 * @author 86740
 */
public interface SysNoticeService extends IService<SysNoticeEntity>{

    void save(SysNoticeAddParams sysNoticeAddParams);

    void update(SysNoticeUpdateParams sysNoticeUpdateParams);

    void delete(Long sysNoticeId);

    SysNoticeVo get(Long sysNoticeId);

    PageResult<SysNoticeVo> page(SysNoticeQueryParams sysNoticeQueryParams);

    List<SysNoticeVo>  getLimit(Integer num);
}
