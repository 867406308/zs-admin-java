package com.zs.sys.notice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.sys.notice.domain.entity.SysNoticeDetailsEntity;
import com.zs.sys.notice.domain.vo.SysNoticeDetailsVo;
import com.zs.sys.notice.mapper.SysNoticeDetailsMapper;
import com.zs.sys.notice.service.SysNoticeDetailsService;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86740
 */
@Service
public class SysNoticeDetailsServiceImpl extends ServiceImpl<SysNoticeDetailsMapper, SysNoticeDetailsEntity> implements SysNoticeDetailsService {
    @Override
    public void save(@NotNull List<Long> receiverIds, Long sysNoticeId) {
        receiverIds.forEach(receiverId -> {
            SysNoticeDetailsEntity sysNoticeDetailsEntity = new SysNoticeDetailsEntity();
            sysNoticeDetailsEntity.setSysNoticeId(sysNoticeId);
            sysNoticeDetailsEntity.setReceiverId(receiverId);
            this.baseMapper.insert(sysNoticeDetailsEntity);
        });
    }

    @Override
    public void update(@NotNull List<Long> receiverIds, Long sysNoticeId) {
        // 先删除在重新添加
        this.baseMapper.delete(new QueryWrapper<SysNoticeDetailsEntity>().eq("sys_notice_id", sysNoticeId));
        this.save(receiverIds, sysNoticeId);
    }

    @Nullable
    @Override
    public List<SysNoticeDetailsVo> list(Long sysNoticeId) {
        return BeanUtil.copyToList(this.baseMapper.list(sysNoticeId), SysNoticeDetailsVo.class);
    }
}
