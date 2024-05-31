package com.zs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author 86740
 */
@SpringBootApplication
@EnableAsync
public class ZsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZsAdminApplication.class, args);
        System.out.println("zs-admin 启动成功");
    }
}
