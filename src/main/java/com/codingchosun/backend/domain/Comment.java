package com.codingchosun.backend.domain;

import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.invalidrequest.UnauthorizedActionException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
            throw new UnauthorizedActionException(ErrorCode.UNAUTHORIZED_ACTION);
        }
    }
}
