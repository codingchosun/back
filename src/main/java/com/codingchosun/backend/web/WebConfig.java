package com.codingchosun.backend.web;

import com.codingchosun.backend.web.argumentresolver.LoginUserArgumentResolver;
import com.codingchosun.backend.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("")
                .excludePathPatterns(
                        "**",
                        "/",
                        "/index.html", "/api/**",
                        "/login", "login/**", "/logout", "/error", "/error/**",
                        "/css/**", "/js/**", "/img/**", "register",
                        "/profile/**"
                );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver());
    }
}
