package com.codingchosun.backend.web;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration

public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/login").permitAll()
                );

        http.httpBasic(AbstractHttpConfigurer::disable);

        http.formLogin(formConfig -> {
            formConfig.usernameParameter("loginId");
            formConfig.passwordParameter("password");
            formConfig.loginPage("/login");
            formConfig.loginProcessingUrl("/login");
            formConfig.successHandler(authenticationSuccessHandler());
            formConfig.failureHandler(authenticationFailureHandler());
        });

        http.logout(logoutConfig -> logoutConfig.logoutSuccessHandler(logoutSuccessHandler()));

//        http.oauth2Login(oauth2Config -> {
//            oauth2Config.successHandler(authenticationSuccessHandler());
//            oauth2Config.failureHandler(authenticationFailureHandler());
//        });

       // http.addFilterAfter(new CsrfCookieFilter(), CsrfFilter.class);

        http.exceptionHandling(exceptionHandleConfig -> exceptionHandleConfig.authenticationEntryPoint(
                new HttpStatusEntryPoint(HttpStatus.FORBIDDEN)));

//        http.authorizeHttpRequests(authorizeRequests -> {
//            authorizeRequests.requestMatchers("/signup", "/login/**", "/oauth2/**")
//                    .permitAll();
//            authorizeRequests.requestMatchers(HttpMethod.GET, "/posts/**")
//                    .permitAll();
//            authorizeRequests.requestMatchers("/roles/**", "/swagger-ui/**")
//                    .hasRole("ADMIN");
//
//            authorizeRequests.requestMatchers("/docs/**")
//                    .hasAnyRole("ADMIN", "MANAGER");
//
//            authorizeRequests.requestMatchers("/ws-zelkova/**")
//                    .permitAll();
//
//            authorizeRequests.anyRequest()
//                    .authenticated();
//        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK);
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK);
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://13.124.154.185"); // 로컬
        configuration.addAllowedOrigin("http://codingchosun.com");// 프론트 IPv4 주소
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("http://localhost:8090");
        configuration.addAllowedMethod("*"); // 모든 메소드 허용.
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setAllowCredentials(true); // 인증 정보를 서버로 전달할지 여부 설정
        configuration.setMaxAge(3600L); // Preflight 요청의 유효 기간 설정 (초 단위)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용

        return source;
    }
}

