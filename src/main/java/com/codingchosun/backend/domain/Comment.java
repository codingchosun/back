package com.codingchosun.backend.domain;

import com.codingchosun.backend.exception.invalidrequest.InvalidEditorException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Setter
    private String content;

    @Setter
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post post;

    public Comment(String content, User user, Post post) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.user = user;
        this.post = post;
    }

    public void validateOwner(User user) {
        if (!this.user.getUserId().equals(user.getUserId())) {
            throw new InvalidEditorException("해당 댓글의 삭제 권한이 없습니다.");
        }
    }
}
