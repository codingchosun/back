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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "userId")
    private User user;
//d
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "postId")
    private Post post;

}
