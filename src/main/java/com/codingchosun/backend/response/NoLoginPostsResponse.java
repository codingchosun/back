package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.Hashtag;
import lombok.*;

import java.util.List;

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
    List<Hashtag> hashtagList;
}
