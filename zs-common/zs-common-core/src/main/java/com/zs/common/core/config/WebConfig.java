package com.zs.common.core.config;



import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

/**
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Value("${file.upload-path}")
    private String resourceLocation;


    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/file/**").addResourceLocations("file:" + resourceLocation);
    }
}
