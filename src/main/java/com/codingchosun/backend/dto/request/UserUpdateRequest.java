package com.codingchosun.backend.dto.request;

import com.codingchosun.backend.constants.GenderCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @JsonProperty("currentPassword")
    private String currentPassword;

    @JsonProperty("newPassword")
    private String newPassword;

    @JsonProperty("email")
    private String email;

    @JsonProperty("genderCode")
    private GenderCode genderCode;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("introduction")
    private String introduction;

    @JsonProperty("hashList")
    private List<String> hashList;
}