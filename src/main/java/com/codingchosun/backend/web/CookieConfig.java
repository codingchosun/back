package com.codingchosun.backend.web;

// Spring Security에서의 쿠키 설정 예시
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class CookieConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("SESSIONID"); // default cookie name is JSESSIONID
        serializer.setDomainName("codingchosun.site");
        return serializer;
    }
}

