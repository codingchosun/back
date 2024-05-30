package com.codingchosun.backend.response;


import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class UserIdAndNickName {
    String userId;
    String nickName;
}
