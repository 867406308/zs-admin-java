package com.zs.sys.notice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.sys.notice.domain.entity.SysNoticeEntity;
import com.zs.sys.notice.domain.params.SysNoticeAddParams;
import com.zs.sys.notice.domain.params.SysNoticeQueryParams;
import com.zs.sys.notice.domain.params.SysNoticeUpdateParams;
import com.zs.sys.notice.domain.vo.SysNoticeVo;
import com.zs.sys.notice.mapper.SysNoticeMapper;
import com.zs.sys.notice.service.SysNoticeDetailsService;
import com.zs.sys.notice.service.SysNoticeService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 86740
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNoticeEntity> implements SysNoticeService {

    @Resource
    private SysNoticeDetailsService sysNoticeDetailsService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(@NotNull SysNoticeAddParams sysNoticeAddParams) {
        SysNoticeEntity sysNoticeEntity = BeanUtil.copyProperties(sysNoticeAddParams, SysNoticeEntity.class);
        if (sysNoticeAddParams.getStatus() == 2) {
            sysNoticeEntity.setReleaseTime(DateUtil.now());
        }
        this.baseMapper.insert(sysNoticeEntity);
        if (!sysNoticeAddParams.getReceiverIds().isEmpty()) {
            //保存通知公告详情
            sysNoticeDetailsService.save(sysNoticeAddParams.getReceiverIds(), sysNoticeEntity.getSysNoticeId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(@NotNull SysNoticeUpdateParams sysNoticeUpdateParams) {
        SysNoticeEntity sysNoticeEntity = BeanUtil.copyProperties(sysNoticeUpdateParams, SysNoticeEntity.class);
        this.baseMapper.updateById(sysNoticeEntity);
        // 修改接收人
        if (!sysNoticeUpdateParams.getReceiverIds().isEmpty()) {
            //保存通知公告详情
            sysNoticeDetailsService.save(sysNoticeUpdateParams.getReceiverIds(), sysNoticeEntity.getSysNoticeId());
        }
    }

    @Override
    public void delete(Long sysNoticeId) {
        SysNoticeEntity sysNoticeEntity = this.baseMapper.selectById(sysNoticeId);
        if (sysNoticeEntity == null) {
            throw new ZsException("通知公告不存在");
        }
        if (sysNoticeEntity.getStatus() == 2) {
            throw new ZsException("已发布的通知公告不能删除");
        }
        this.baseMapper.deleteById(sysNoticeId);
    }

    @NotNull
    @Override
    public SysNoticeVo get(Long sysNoticeId) {
        SysNoticeEntity sysNoticeEntity = this.baseMapper.get(sysNoticeId);
        SysNoticeVo sysNoticeVo = BeanUtil.copyProperties(sysNoticeEntity, SysNoticeVo.class);

        sysNoticeVo.setSysNoticeDetailsVos(sysNoticeDetailsService.list(sysNoticeId));
        return sysNoticeVo;
    }

    @NotNull
    @Override
    public PageResult<SysNoticeVo> page(@NotNull SysNoticeQueryParams sysNoticeQueryParams) {
        Page<SysNoticeEntity> pageResult = new PageInfo<>(sysNoticeQueryParams);
        Map<String, Object> params = BeanUtil.beanToMap(sysNoticeQueryParams);
        IPage<SysNoticeEntity> page = this.baseMapper.page(pageResult, params);
        List<SysNoticeVo> list = BeanUtil.copyToList(page.getRecords(), SysNoticeVo.class);
        return new PageResult<>(list, page.getTotal());
    }

    @Nullable
    @Override
    public List<SysNoticeVo> getLimit(Integer num) {
        List<SysNoticeEntity> list =  this.baseMapper.selectList(new QueryWrapper<SysNoticeEntity>()
                                    .eq("status", 2)
                                    .orderByDesc("release_time")
                                    .last("limit " + num));
        return BeanUtil.copyToList(list, SysNoticeVo.class);
    }

}
