package com.codingchosun.backend.response;

import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class NoLoginPostsResponse {
    Long id;
    String title;
    String contents;
    String path;
}
