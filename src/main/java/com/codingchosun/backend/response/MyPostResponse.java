package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyPostResponse {

    private Long postId;
    private String title;
    private LocalDateTime createAt;
    private String host;

    public static MyPostResponse from(Post post) {
        return new MyPostResponse(
                post.getPostId(),
                post.getTitle(),
                post.getCreatedAt(),
                post.getUser().getNickname()
        );
    }
}
