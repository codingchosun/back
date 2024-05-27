package com.codingchosun.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotEmpty
    @JsonProperty("loginId")
    private String loginId;

    @NotEmpty
    @JsonProperty("password")
    private String password;
}
