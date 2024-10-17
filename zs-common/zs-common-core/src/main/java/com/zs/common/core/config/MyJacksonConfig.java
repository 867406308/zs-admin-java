package com.zs.common.core.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class MyJacksonConfig {


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeObjectMapper() {
        return builder -> {
            // 构建 ObjectMapper 实例
            ObjectMapper objectMapper = builder.build();

            // 禁用美化输出
            objectMapper.disable(SerializationFeature.INDENT_OUTPUT);

            // 设置时区和日期格式
            objectMapper.setLocale(Locale.CHINA);
            objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            objectMapper.registerModule(new JavaTimeModule());

            // 显式设置序列化包含 null 值
            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

            // 自定义序列化器处理 String 类型的 null 值
            SimpleModule stringModule = new SimpleModule();
            stringModule.addSerializer(String.class, new JsonSerializer<>() {
                @Override
                public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeString(value == null ? "" : value);
                }
            });
            objectMapper.registerModule(stringModule);

            // 自定义序列化器处理 Boolean 类型的 null 值
            SimpleModule booleanModule = new SimpleModule();
            booleanModule.addSerializer(Boolean.class, new JsonSerializer<>() {
                @Override
                public void serialize(Boolean value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeBoolean(value != null && value);
                }
            });
            objectMapper.registerModule(booleanModule);

            // 忽略 JSON 中存在但 Java 对象不存在的字段
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // 允许反序列化时未找到属性时不抛出异常
            objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);

            // 配置 ObjectMapper 的全局设置
            builder.failOnEmptyBeans(false); // 忽略空的 Bean
            builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS); // 忽略空 Bean 错误
            builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // 忽略未知属性
            builder.featuresToDisable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES); // 忽略缺失创建者属性

            // 将 Long 类型序列化为字符串
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);

        };
    }
}
