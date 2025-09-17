package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchPostResponse {

    private Long id;
    private String title;
    private String contents;
    private String path;

    public static SearchPostResponse from(Post post, String imageUrl) {
        return new SearchPostResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                imageUrl
        );
    }
}
