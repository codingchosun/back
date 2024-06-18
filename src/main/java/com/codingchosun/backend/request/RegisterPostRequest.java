package com.codingchosun.backend.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPostRequest {
    @NotNull
    private String title;
    @NotNull
    private String content;

    //모임 시작 시간
    @NotNull
    private LocalDateTime startTime;

    private List<String> hashtags = new ArrayList<>();

    @Override
    public String toString() {
        return "RegisterPostRequest{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", startTime=" + startTime +
                ", hashtags=" + hashtags +
                '}';
    }
}
