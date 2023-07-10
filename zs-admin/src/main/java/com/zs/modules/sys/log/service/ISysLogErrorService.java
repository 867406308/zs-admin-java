package com.zs.modules.sys.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.log.domain.entity.SysLogErrorEntity;
import com.zs.modules.sys.log.domain.params.SysLogErrorQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogErrorVo;

/**
 * @author 86740
 */
public interface ISysLogErrorService extends IService<SysLogErrorEntity> {

    PageResult<SysLogErrorVo> page(SysLogErrorQueryParams sysLogErrorQueryParams);
}
