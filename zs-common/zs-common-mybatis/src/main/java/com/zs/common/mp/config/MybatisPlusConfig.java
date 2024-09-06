package com.zs.common.mp.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zs.common.mp.handler.MyDataPermissionHandler;
import com.zs.common.mp.interceptor.MyDataPermissionInterceptor;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 86740
 */
@Configuration
public class MybatisPlusConfig {



    @NotNull
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 防止全表更新和删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 数据权限插件
        MyDataPermissionInterceptor myDataPermissionInterceptor = new MyDataPermissionInterceptor();
        myDataPermissionInterceptor.setDataPermissionHandler(new MyDataPermissionHandler());

        interceptor.addInnerInterceptor(myDataPermissionInterceptor);
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }


}
