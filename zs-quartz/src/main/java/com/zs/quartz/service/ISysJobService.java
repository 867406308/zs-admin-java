package com.zs.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.page.PageResult;
import com.zs.quartz.domain.entity.SysJobEntity;
import com.zs.quartz.domain.params.SysJobAddParams;
import com.zs.quartz.domain.params.SysJobQueryParams;
import com.zs.quartz.domain.params.SysJobUpdateParams;
import com.zs.quartz.domain.vo.SysJobVo;

import java.util.List;

/**
 * 定时任务接口
 */
public interface ISysJobService extends IService<SysJobEntity> {

    PageResult<SysJobVo> page(SysJobQueryParams sysJobQueryParams);

    /**
     * 新增定时任务
     */
    void save(SysJobAddParams sysJobAddParams);

    /**
     * 更新定时任务
     */
    void update(SysJobUpdateParams sysJobUpdateParams);

    /**
     * 定时任务列表
     */
    List<SysJobVo> list(SysJobEntity sysJobEntity);

    /**
     * 获取定时任务详情
     */
    SysJobVo get(Long sysJobId);

    /**
     * 删除定时任务
     */
    void del(Long sysJobId);

    /**
     * 暂停定时任务
     */
    void pause(SysJobUpdateParams sysJobUpdateParams);

    /**
     * 恢复定时任务
     */
    void resume(SysJobUpdateParams sysJobUpdateParams);

    /**
     * 立即执行一次
     */
    void run(SysJobUpdateParams sysJobUpdateParams);


}
