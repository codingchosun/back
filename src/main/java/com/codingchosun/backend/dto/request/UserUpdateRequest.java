package com.codingchosun.backend.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

    @Size(min = 2, max = 12, message = "닉네임은 2자 이상 12자 이하로 입력하세요.")
    private String nickname;
    private String introduction;
    private List<String> hashtags;
    private String currentPassword;
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "비밀번호는 영문자와 숫자만 사용할 수 있습니다.")
    private String newPassword;

}