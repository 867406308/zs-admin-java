package com.zs.modules.sys.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.log.domain.entity.SysLogOperationEntity;
import com.zs.modules.sys.log.domain.params.SysLogOperationQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogOperationVo;

/**
 * @author 86740
 */
public interface ISysLogOperationService extends IService<SysLogOperationEntity> {

    PageResult<SysLogOperationVo> page(SysLogOperationQueryParams sysLogOperationQueryParams);
}
