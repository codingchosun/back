package com.codingchosun.backend.response;

import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class ResearchPostResponse {
    Long id;
    String title;
    String contents;
    String path;
}
