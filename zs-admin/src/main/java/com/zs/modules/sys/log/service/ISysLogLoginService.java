package com.zs.modules.sys.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.log.domain.entity.SysLogLoginEntity;
import com.zs.modules.sys.log.domain.params.SysLogLoginQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogLoginVo;

public interface ISysLogLoginService extends IService<SysLogLoginEntity> {

    PageResult<SysLogLoginVo> page(SysLogLoginQueryParams sysLogLoginQueryParams);
}
