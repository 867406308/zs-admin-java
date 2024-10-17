package com.zs.modules.sys.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.log.params.SysLogOperationAddParams;
import com.zs.common.core.page.PageResult;
import com.zs.modules.sys.log.domain.entity.SysLogOperationEntity;
import com.zs.modules.sys.log.domain.params.SysLogOperationQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogOperationVo;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * @author 86740
 */
public interface ISysLogOperationService extends IService<SysLogOperationEntity> {

    void save(SysLogOperationAddParams sysLogOperationAddParams);

    PageResult<SysLogOperationVo> page(SysLogOperationQueryParams sysLogOperationQueryParams);

    /**
     * 获取操作日志集合
     */
    @Nullable
    List<SysLogOperationVo> list(SysLogOperationQueryParams sysLogOperationQueryParams);
}
