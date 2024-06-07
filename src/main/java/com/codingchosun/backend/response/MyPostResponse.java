package com.codingchosun.backend.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MyPostResponse {
    private Long postId;
    private String title;
    private LocalDateTime createAt;
    private String author;

    @Builder
    public MyPostResponse(Long postId, String title, LocalDateTime createAt, String author) {
        this.postId = postId;
        this.title = title;
        this.createAt = createAt;
        this.author = author;
    }
}
