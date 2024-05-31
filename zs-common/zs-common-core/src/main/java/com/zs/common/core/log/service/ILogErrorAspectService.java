package com.zs.common.core.log.service;


import com.zs.common.core.log.params.SysLogErrorAddParams;

/**
 * @author 86740
 */
public interface ILogErrorAspectService {
    void save(SysLogErrorAddParams sysLogErrorAddParams);

}
