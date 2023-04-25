package com.zs.framework.config;

import com.zs.framework.security.filter.MyAuthenticationFilter;
import com.zs.framework.security.filter.JwtAuthenticationTokenFilter;
import com.zs.framework.security.handler.*;
import com.zs.framework.security.propetties.WhiteUrlProperties;
import com.zs.framework.security.provider.UserNameAuthenticationProvider;
import com.zs.framework.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)    // 启用方法级别的权限认证
public class WebSecurityConfig {

    /**
     * 退出处理类
     */
    @Resource
    private CustomLogoutSuccessHandler logoutSuccessHandler;

    //    @Resource
//    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
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

    //    /**
//     * 密码明文加密方式配置
//     * @return
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
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
    public MyAuthenticationFilter myAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        MyAuthenticationFilter myAuthenticationFilter = new MyAuthenticationFilter("/login");
        myAuthenticationFilter.setAuthenticationManager(authenticationManager);
        myAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        myAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        return myAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        List<String> whiteUrl = whiteUrlProperties.getUrl();
        String[] urls = whiteUrl.stream().toArray(String[]::new);
        http

                // 禁用csrf
                .csrf().disable()
                .cors().and()
                // 禁止session
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 白名单url，匿名访问
                .authorizeHttpRequests(auth -> auth
                        .mvcMatchers(urls).permitAll()
                        .mvcMatchers(HttpMethod.OPTIONS).permitAll() // 对option不校验
                        // 不在白名单的请求都需要身份认证
                        .anyRequest().authenticated()

                )
                // 把token校验过滤器添加到过滤器链中
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(myAuthenticationFilter(http.getSharedObject(AuthenticationManager.class)), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(userNameAuthenticationProvider)
                // 自定义异常处理
                .exceptionHandling()
                // 认证失败
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                // 鉴权失败
                .accessDeniedHandler(customAccessDeniedHandler);
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
