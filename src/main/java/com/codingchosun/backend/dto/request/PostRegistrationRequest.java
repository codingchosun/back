package com.codingchosun.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRegistrationRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private LocalDateTime startTime;

    private List<String> hashtags;
}
