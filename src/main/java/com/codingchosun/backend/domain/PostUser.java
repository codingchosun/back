package com.codingchosun.backend.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table
@EqualsAndHashCode
public class PostUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post post;

}
