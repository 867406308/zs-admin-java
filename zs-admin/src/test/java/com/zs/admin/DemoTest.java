package com.zs.admin;

import cn.hutool.core.bean.BeanUtil;
import com.zs.modules.sys.log.domain.entity.SysLogLoginEntity;
import com.zs.modules.sys.log.domain.params.SysLogLoginAddParams;
import com.zs.modules.sys.log.mapper.SysLogLoginMapper;
import com.zs.modules.sys.role.domain.entity.SysRoleEntity;
import com.zs.modules.sys.role.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class DemoTest {

    @Autowired
    private SysLogLoginMapper sysLoginLogMapper;

    @Test
    public void  test(){
        SysLogLoginAddParams sysLoginLogAddParams = new SysLogLoginAddParams();
        sysLoginLogAddParams.setUsername("111");
        sysLoginLogAddParams.setIpAddress("111");
        sysLoginLogAddParams.setCity("111");
        sysLoginLogAddParams.setUserAgent("111");
        sysLoginLogAddParams.setLoginStatus(1);
        sysLoginLogAddParams.setFailureReason(null);
        sysLoginLogAddParams.setLoginMethod(1);
        sysLoginLogAddParams.setLoginSource(null);
        sysLoginLogAddParams.setBrowser("111");
        sysLoginLogAddParams.setOs("111");

        SysLogLoginEntity sysLoginLogEntity = BeanUtil.copyProperties(sysLoginLogAddParams, SysLogLoginEntity.class);

        sysLoginLogMapper.insert(sysLoginLogEntity);
    }

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Test
    public void test1(){
        SysRoleEntity sysRoleEntity = new SysRoleEntity();
        sysRoleEntity.setRoleName("ceshi");

        sysRoleMapper.insert(sysRoleEntity);


    }
}
