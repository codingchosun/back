package com.codingchosun.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postUserId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post post;

    public PostUser(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
