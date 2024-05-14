package com.codingchosun.backend.request;

import com.codingchosun.backend.constants.GenderCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@Generated
public class RegisterUserRequest {
    private String name;
    private String loginId;
    private String password;
    private String email;
    private GenderCode genderCode;
    private LocalDate birth;
    private String nickname;

}
