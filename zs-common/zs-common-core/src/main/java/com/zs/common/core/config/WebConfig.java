package com.zs.common.core.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        // 1. Create a new ObjectMapper for Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        // 2. Configure the ObjectMapper
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false); // Do not write null map values
        objectMapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true); // Unwrap single element arrays
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // Exclude null values

        // Date format configuration
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Write dates as ISO-8601
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")); // Custom date format

        // 3. Create a new MappingJackson2HttpMessageConverter
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);

        // 4. Set the supported media types
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8); // If you need UTF-8 support
        jacksonConverter.setSupportedMediaTypes(supportedMediaTypes);

        // 将 Long 类型序列化为字符串
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        // 5. Add the converter to the list
        converters.add(jacksonConverter);
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = converter.getObjectMapper();
//
//        // 配置ObjectMapper
//        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        // 格式化日期
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//
//        // 注册JavaTimeModule模块来处理Java 8日期和时间API
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
//        objectMapper.registerModule(javaTimeModule);
//
//        // 处理Number类型为null时的情况
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//        simpleModule.addSerializer(Double.class, new StdSerializer<Double>(Double.class) {
//            @Override
//            public void serialize(Double value, JsonGenerator gen, SerializerProvider provider) throws IOException {
//                if (value == null) {
//                    gen.writeNumber(0);
//                } else {
//                    gen.writeNumber(value);
//                }
//            }
//        });
//        objectMapper.registerModule(simpleModule);
//
//        // 设置支持的MediaType
//        List<MediaType> mediaTypes = Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8);
//        converter.setSupportedMediaTypes(mediaTypes);
//
//        // 更新converter的objectMapper
//        converter.setObjectMapper(objectMapper);
//
//        // 添加到converters中
//        converters.add(converter);
////        converters.add(new CustomJsonHttpMessageConverter());
//    }
}
