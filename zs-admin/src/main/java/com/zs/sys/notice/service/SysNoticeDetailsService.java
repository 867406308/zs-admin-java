package com.zs.sys.notice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.sys.notice.domain.entity.SysNoticeDetailsEntity;
import com.zs.sys.notice.domain.vo.SysNoticeDetailsVo;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * @author 86740
 */
public interface SysNoticeDetailsService extends IService<SysNoticeDetailsEntity> {

    void save(List<Long> receiverIds, Long sysNoticeId);

    void update(List<Long> receiverIds, Long sysNoticeId);

    @Nullable
    List<SysNoticeDetailsVo> list(Long sysNoticeId);
}
