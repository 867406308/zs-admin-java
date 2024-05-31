package com.zs.common.security.config;


import com.zs.common.security.filter.JwtAuthenticationTokenFilter;
import com.zs.common.security.filter.MyAuthenticationFilter;
import com.zs.common.security.handler.*;
import com.zs.common.security.propetties.WhiteUrlProperties;
import com.zs.common.security.provider.UserNameAuthenticationProvider;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


/**
 * @author 86740
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // 启用方法级别的权限认证
public class WebSecurityConfig {

    /**
     * 退出处理类
     */
    @Resource
    private CustomLogoutSuccessHandler logoutSuccessHandler;


    // 无权限失败处理
    @Resource
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    // 用户名密码登录成功处理
    @Resource
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    // 用户名密码登录失败处理
    @Resource
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Resource
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Resource
    private UserNameAuthenticationProvider userNameAuthenticationProvider;
    @Resource
    private WhiteUrlProperties whiteUrlProperties;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //加入token验证过滤器
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthorizationFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public MyAuthenticationFilter myAuthenticationFilter(AuthenticationManager authenticationManager) {
        MyAuthenticationFilter myAuthenticationFilter = new MyAuthenticationFilter("/auth/login");
        myAuthenticationFilter.setAuthenticationManager(authenticationManager);
        myAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        myAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        return myAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        List<String> whiteUrl = whiteUrlProperties.getUrl();
        String[] urls = whiteUrl.toArray(new String[0]);
        http

                // 禁用csrf
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 禁止session
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 禁用iframe。默认deny为限制iframe,disable为不限制
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                // 白名单url，匿名访问
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/**").permitAll() // 允许对其他端点进行公共访问
                                .requestMatchers("/webjars/**", "/swagger-resources/**", "/v3/**").permitAll()
                                .requestMatchers(urls).permitAll()
                                .requestMatchers(HttpMethod.OPTIONS).permitAll() // 对option不校验
                                // 不在白名单的请求都需要身份认证
                                .anyRequest().authenticated()

                )
                // 自定义异常处理
                .exceptionHandling(exception -> exception
                        // 认证失败
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        // 鉴权失败
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .logout(logout -> logout.logoutUrl("/auth/logout").permitAll().logoutSuccessHandler(logoutSuccessHandler))
                // 把token校验过滤器添加到过滤器链中
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(myAuthenticationFilter(http.getSharedObject(AuthenticationManager.class)), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(userNameAuthenticationProvider)

        ;
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}