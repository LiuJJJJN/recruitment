package com.ibm.rms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer MVC 配置类
 *
 * @author 刘嘉宁
 * @date 2022-11-29 11:45:30
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域解决: 允许所有请求、所有请求头、所有方式的请求通过
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }

}
