package com.codingchosun.backend.dto.response;

import com.codingchosun.backend.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginPostsResponse {

    private Long id;
    private String title;
    private String contents;
    private String path;

    public static LoginPostsResponse from(Post post, String imageUrl) {
        return new LoginPostsResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                imageUrl
        );
    }
}
