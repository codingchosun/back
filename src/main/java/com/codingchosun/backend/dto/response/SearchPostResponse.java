package com.codingchosun.backend.dto.response;

import com.codingchosun.backend.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchPostResponse {

    private Long postId;
    private String title;
    private String contents;
    private String url;

    public static SearchPostResponse from(Post post, String imageUrl) {
        return new SearchPostResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                imageUrl
        );
    }
}
