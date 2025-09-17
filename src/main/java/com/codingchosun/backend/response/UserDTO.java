package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;
    private String loginId;
    private String nickname;

    public static UserDTO from(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getLoginId(),
                user.getNickname()
        );
    }
}
