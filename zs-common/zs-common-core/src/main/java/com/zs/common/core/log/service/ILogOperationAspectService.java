package com.zs.common.core.log.service;


import com.zs.common.core.log.params.SysLogOperationAddParams;

/**
 * @author 86740
 */
public interface ILogOperationAspectService {
    void save(SysLogOperationAddParams sysLogOperationAddParams);

}
