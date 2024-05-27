package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.Hashtag;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class LoginPostsHashtagResponse {
    Page<LoginPostsResponse> loginPostsResponses;
    List<HashtagDto> hashtagDtoList;


}
