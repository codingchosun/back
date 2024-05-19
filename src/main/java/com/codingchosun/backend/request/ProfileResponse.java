package com.codingchosun.backend.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponse {

    @NotEmpty
    private String nickname;

    private String introduction;

    @NotEmpty
    private String email;

    private int score;

    private List<String> hashNames;

    private List<String> templateNames;
}
