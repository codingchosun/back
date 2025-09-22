package com.codingchosun.backend.dto.response;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoLoginPostsHashtagsResponse {

    Page<NoLoginPostsResponse> posts;
    List<HashtagDto> hashtags;

}
