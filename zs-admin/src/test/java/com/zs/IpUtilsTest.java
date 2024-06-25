package com.zs;


import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.math.RoundingMode;


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
}
