package com.codingchosun.backend.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostUpdateRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private LocalDateTime startTime;    //약속시간

    private List<String> addTags;   //글에 추가될 태그들
    private List<String> removeTags;   //삭제될 태그들

    // 이미지 추가는 posts/{postId}/images로 추가하길 바람
    private List<Long> removeImages;    //삭제될 이미지 id의 리스트
}
