package com.codingchosun.backend.request;

import com.codingchosun.backend.constants.GenderCode;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@Generated
@NoArgsConstructor
public class RegisterUserRequest {
    private String name;
    private String loginId;
    private String password;
    private String email;
    private GenderCode genderCode;
    private LocalDate birth;
    private String nickname;

}
