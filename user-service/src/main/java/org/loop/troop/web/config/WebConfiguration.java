package org.loop.troop.web.config;

import lombok.RequiredArgsConstructor;
import org.loop.troop.handler.PermitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:config.properties")
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final PermitInterceptor permitInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permitInterceptor)
                .addPathPatterns("/**");
    }
}
