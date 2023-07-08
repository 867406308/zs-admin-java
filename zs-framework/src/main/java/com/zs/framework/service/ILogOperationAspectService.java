package com.zs.framework.service;

import com.zs.common.model.params.SysLogLoginAddParams;
import com.zs.common.model.params.SysLogOperationAddParams;

public interface ILogOperationAspectService {
    void save(SysLogOperationAddParams sysLogOperationAddParams);

}
