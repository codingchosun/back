package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserToEvaluateDto {

    private Long userId;
    private String nickname;

    public static UserToEvaluateDto from(User user) {
        return new UserToEvaluateDto(
                user.getUserId(),
                user.getNickname()
        );
    }
}
