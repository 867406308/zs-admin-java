package com.zs;


import com.zs.sys.role.domain.entity.SysRoleEntity;
import com.zs.sys.role.mapper.SysRoleMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IpUtilsTest {

    @Test
    public void test() {
        BigDecimal total = new BigDecimal(190);

        System.out.println(total.divide(new BigDecimal(6), 2, RoundingMode.HALF_EVEN));

        System.out.println(new BigDecimal(190).subtract(new BigDecimal(6)));
    }

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void test1() {
        System.out.println(passwordEncoder.encode("Zs@123.."));
    }

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Test
    public void test3() {
        List<SysRoleEntity> deptIds = sysRoleMapper.getList(1633833655917559809L);
        System.out.println("deptIds  " + deptIds);
    }
}
