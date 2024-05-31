package com.zs.sys.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.log.params.SysLogOperationAddParams;
import com.zs.common.core.page.PageResult;
import com.zs.sys.log.domain.entity.SysLogOperationEntity;
import com.zs.sys.log.domain.params.SysLogOperationQueryParams;
import com.zs.sys.log.domain.vo.SysLogOperationVo;

/**
 * @author 86740
 */
public interface ISysLogOperationService extends IService<SysLogOperationEntity> {

    void save(SysLogOperationAddParams sysLogOperationAddParams);

    PageResult<SysLogOperationVo> page(SysLogOperationQueryParams sysLogOperationQueryParams);
}
