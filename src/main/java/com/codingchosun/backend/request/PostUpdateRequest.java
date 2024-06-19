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

    @NotEmpty
    private String alterTags; //스페이스로 구분한 해쉬태그

}
