package com.zs.modules.sys.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.log.domain.entity.SysLogErrorEntity;
import com.zs.modules.sys.log.domain.params.SysLogErrorQueryParams;
import com.zs.modules.sys.log.domain.vo.SysLogErrorVo;
import com.zs.modules.sys.post.domain.query.SysPostQueryParams;
import com.zs.modules.sys.post.domain.vo.SysPostVo;

public interface ISysLogErrorService extends IService<SysLogErrorEntity> {

    PageResult<SysLogErrorVo> page(SysLogErrorQueryParams sysLogErrorQueryParams);
}
