package com.codingchosun.backend.response;

import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class LoginPostsRequest {
    Long id;
    String title;
    String contents;
    String path;
}
