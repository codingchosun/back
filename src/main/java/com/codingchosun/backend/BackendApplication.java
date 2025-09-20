package com.codingchosun.backend;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class BackendApplication {

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        log.info("현재 시각: {}", new Date());
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
