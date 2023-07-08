package com.zs.framework.service;

import com.zs.common.model.params.SysLogErrorAddParams;
import com.zs.common.model.params.SysLogLoginAddParams;
import com.zs.common.model.params.SysLogOperationAddParams;

public interface ILogLoginAspectService {
    void save(SysLogLoginAddParams sysLogLoginAddParams);

}
