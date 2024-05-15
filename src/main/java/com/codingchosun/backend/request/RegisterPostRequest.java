package com.codingchosun.backend.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<String> hashtags = new ArrayList<>();
}
