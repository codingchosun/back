package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoLoginPostsResponse {

    private Long id;
    private String title;
    private String contents;
    private String path;

    public static NoLoginPostsResponse from(Post post, String imageUrl) {
        return new NoLoginPostsResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                imageUrl
        );
    }
}
