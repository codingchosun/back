package com.codingchosun.backend.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class NoLoginPostsRequest {
    Long id;
    String title;
    String contents;
    String path;
}
