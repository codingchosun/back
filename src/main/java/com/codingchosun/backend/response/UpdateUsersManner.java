package com.codingchosun.backend.response;

import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class UpdateUsersManner {
    String nickname;
    Integer score;
}
