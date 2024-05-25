package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.User;
import lombok.Data;

@Data
public class UserDTO {

    private Long userId;
    private String nickname;

    public UserDTO(User user){
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
    }

}
