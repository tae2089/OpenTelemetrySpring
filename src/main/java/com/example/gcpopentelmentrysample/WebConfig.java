package com.example.gcpopentelmentrysample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer {

    @Autowired
    private OpenTelemetryComponent openTelemetryComponent;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new OpenTelemetryInterceptor(openTelemetryComponent))
                .addPathPatterns("/*");
    }
}
