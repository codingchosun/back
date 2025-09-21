package com.codingchosun.backend.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginCheckInterceptor())
//                .order(1)
//                .addPathPatterns("")
//                .excludePathPatterns(
//                        "**",
//                        "/",
//                        "/index.html", "/api/**",
//                        "/login", "login/**", "/logout", "/error", "/error/**",
//                        "/css/**", "/js/**", "/img/**", "register",
//                        "/profile/**"
//                );
//    }

//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(new LoginUserArgumentResolver());
//    }
}
