package com.codingchosun.backend.dto.response;

import com.codingchosun.backend.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private String nickname;
    private Long loginId;

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUser().getNickname(),
                comment.getUser().getUserId()
        );
    }
}
