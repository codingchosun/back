package com.codingchosun.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post post;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Hashtag hashtag;

    public PostHash(Post post, Hashtag hashtag) {
        this.post = post;
        this.hashtag = hashtag;
    }
}