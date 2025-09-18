package com.codingchosun.backend.dto.response;

import com.codingchosun.backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindUserResponse {

    private Long userId;

    private String loginId;

    public static FindUserResponse from(User user) {
        return new FindUserResponse(
                user.getUserId(),
                user.getLoginId()
        );
    }
}
