package com.zs.framework.service;

import com.zs.common.model.params.SysLogErrorAddParams;
import com.zs.common.model.params.SysLogLoginAddParams;

public interface ILogErrorAspectService {
    void save(SysLogErrorAddParams sysLogErrorAddParams);

}
