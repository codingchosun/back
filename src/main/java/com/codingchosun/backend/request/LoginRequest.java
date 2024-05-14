package com.codingchosun.backend.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
