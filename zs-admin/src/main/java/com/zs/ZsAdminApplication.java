package com.zs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author 86740
 */
@EnableAsync // 开启异步注解功能
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },scanBasePackages = {"org.jeecg.modules.jmreport","com.zs"})
public class ZsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZsAdminApplication.class, args);
        System.out.println("zs-admin 启动成功");
    }
}
