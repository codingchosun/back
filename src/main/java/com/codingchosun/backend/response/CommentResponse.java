package com.codingchosun.backend.response;

import com.codingchosun.backend.domain.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private UserDTO userDTO;

    public CommentResponse(Comment comment){
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.userDTO = new UserDTO(comment.getUser());
    }
}
