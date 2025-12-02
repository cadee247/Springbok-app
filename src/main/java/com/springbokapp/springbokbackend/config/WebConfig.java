package com.springbokapp.springbokbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // This exposes the real folder so the browser can access uploaded images
        registry.addResourceHandler("/uploads/memes/**")
                .addResourceLocations("file:uploads/memes/");

        // If you have a placeholder, map /images/** as well
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }
}
