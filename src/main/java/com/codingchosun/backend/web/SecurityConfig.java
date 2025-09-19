package com.codingchosun.backend.web;

import com.codingchosun.backend.security.AccountDetailService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AccountDetailService accountDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)

                .logout(logoutConfig -> logoutConfig.logoutSuccessHandler(logoutSuccessHandler()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

//        http.oauth2Login(oauth2Config -> {
//            oauth2Config.successHandler(authenticationSuccessHandler());
//            oauth2Config.failureHandler(authenticationFailureHandler());
//        });

        // http.addFilterAfter(new CsrfCookieFilter(), CsrfFilter.class);

        http.exceptionHandling(exceptionHandleConfig -> exceptionHandleConfig.authenticationEntryPoint(
                new HttpStatusEntryPoint(HttpStatus.FORBIDDEN)));

        http.authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests.requestMatchers(
                            "/", "/error",
                            "/register", "/login", "logout",
                            "/users/login-id", "/users/password",
                            "/swagger-ui/**", "/v3/api-docs/**"
                    ).permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/mypage", "/useredit", "/myposts").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/posts/**", "/profile/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/posts/**", "/comments/**").hasRole("USER")
                    .requestMatchers(HttpMethod.PUT, "/profile/**").hasRole("USER")
                    .anyRequest().authenticated();

//            authorizeRequests.requestMatchers("/oauth2/**").permitAll();
//            authorizeRequests.requestMatchers("/ws-zelkova/**").permitAll();
        });

        http.userDetailsService(accountDetailService);

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
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://13.124.154.185"); // 로컬
        configuration.addAllowedOrigin("http://codingchosun.site");// 프론트 IPv4 주소
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("http://localhost:8090");
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*"); // 모든 메소드 허용.
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true); // 인증 정보를 서버로 전달할지 여부 설정
        configuration.setMaxAge(3600L); // Preflight 요청의 유효 기간 설정 (초 단위)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용

        return source;
    }
}

