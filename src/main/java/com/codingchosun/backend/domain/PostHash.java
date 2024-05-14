package com.codingchosun.backend.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter @Setter
@Table
@EqualsAndHashCode
public class PostHash {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtagId")
    private Hashtag hashtag;

}