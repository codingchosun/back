package com.codingchosun.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginPostsHashtagResponse {

    Page<LoginPostsResponse> posts;
    List<HashtagDto> hashtags;

}
