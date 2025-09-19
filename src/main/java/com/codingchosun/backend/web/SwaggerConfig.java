package com.codingchosun.backend.web;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("팀 CODINGCHOSUN의 API 문서")
                .version("v1.0.0")
                .description("소규모 모임 플랫폼 API 명세서입니다.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
