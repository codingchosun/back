package com.codingchosun.backend.request;

import com.codingchosun.backend.constants.GenderCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Generated
@Repository
@ToString
public class UserUpdateRequest {
    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("genderCode")
    private GenderCode genderCode;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("introduction")
    private String introduction;

    @Builder
    public UserUpdateRequest(String password, String email, String nickname, String introduction, GenderCode genderCode) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.introduction = introduction;
        this.genderCode = genderCode;
    }
}