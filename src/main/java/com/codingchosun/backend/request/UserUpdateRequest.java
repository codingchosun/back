package com.codingchosun.backend.request;


import com.codingchosun.backend.constants.GenderCode;
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
    private String password;
    private String email;
    private GenderCode genderCode;
    private String nickname;
    private String introduction;
}
