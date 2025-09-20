package com.codingchosun.backend.dto.response;

import lombok.Getter;

@Getter
public class PostUpdateResponse {

    private final Long postId;

    public PostUpdateResponse(Long postId) {
        this.postId = postId;
    }
}
