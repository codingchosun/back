package com.codingchosun.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPostRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Setter
    @NotNull
    private LocalDateTime startTime;

    private List<String> hashtags;
}
