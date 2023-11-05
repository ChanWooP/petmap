package com.cwpark.petmap.petmap.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler( "/files/**") // 경로 설정
                .addResourceLocations("file:///C:/uploadFile/"); // 실제 경로(window 버전)
    }
}
