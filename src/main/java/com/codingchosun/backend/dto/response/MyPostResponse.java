package com.codingchosun.backend.dto.response;

import com.codingchosun.backend.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPostResponse {

    private Long postId;
    private String title;
    private LocalDateTime createdAt;
    private String nickname;

    public MyPostResponse(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
        this.nickname = post.getUser().getNickname();
    }

    public static MyPostResponse from(Post post) {
        return new MyPostResponse(
                post.getPostId(),
                post.getTitle(),
                post.getCreatedAt(),
                post.getUser().getNickname()
        );
    }
}
