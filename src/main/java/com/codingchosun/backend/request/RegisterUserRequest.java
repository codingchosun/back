package com.codingchosun.backend.request;

import com.codingchosun.backend.constants.GenderCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RegisterUserRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("loginId")
    private String loginId;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("genderCode")
    private GenderCode genderCode;

    @JsonProperty("birth")
    private LocalDate birth;

    @JsonProperty("nickname")
    private String nickname;
}
