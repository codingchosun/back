package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.User;
import lombok.Data;

@Data
public class UserDTO {

    private Long userId;
    private String nickname;
    private String loginId;

    public UserDTO(User user){
        this.userId = user.getUserId();
        this.loginId = user.getLoginId();
        this.nickname = user.getNickname();
    }

}
