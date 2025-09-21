package com.codingchosun.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotEmpty
    @JsonProperty("loginId")
    private String loginId;

    @NotEmpty
    @JsonProperty("password")
    private String password;
}
