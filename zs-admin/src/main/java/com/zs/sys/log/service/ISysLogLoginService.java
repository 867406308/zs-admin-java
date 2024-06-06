package com.zs.sys.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.log.params.SysLogLoginAddParams;
import com.zs.common.core.page.PageResult;
import com.zs.sys.log.domain.entity.SysLogLoginEntity;
import com.zs.sys.log.domain.params.SysLogLoginQueryParams;
import com.zs.sys.log.domain.vo.SysLogLoginVo;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysLogLoginService extends IService<SysLogLoginEntity> {

    void save(SysLogLoginAddParams sysLogLoginAddParams);

    PageResult<SysLogLoginVo> page(SysLogLoginQueryParams sysLogLoginQueryParams);

    /**
     * 获取今日登录日志
     */
    List<SysLogLoginVo> todayList();
}