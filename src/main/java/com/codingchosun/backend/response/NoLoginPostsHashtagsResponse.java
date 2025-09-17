package com.codingchosun.backend.response;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoLoginPostsHashtagsResponse {

    Page<NoLoginPostsResponse> posts;
    List<HashtagDto> randomHashtags;

}
