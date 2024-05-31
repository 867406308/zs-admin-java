package com.zs.sys.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.log.params.SysLogErrorAddParams;
import com.zs.common.core.page.PageResult;
import com.zs.sys.log.domain.entity.SysLogErrorEntity;
import com.zs.sys.log.domain.params.SysLogErrorQueryParams;
import com.zs.sys.log.domain.vo.SysLogErrorVo;


/**
 * @author 86740
 */
public interface ISysLogErrorService extends IService<SysLogErrorEntity> {

    void save(SysLogErrorAddParams sysLogErrorAddParams);

    PageResult<SysLogErrorVo> page(SysLogErrorQueryParams sysLogErrorQueryParams);
}
