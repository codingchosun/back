package com.codingchosun.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@Table
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Validate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long validateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "templateId")
    private Template template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fromUserId")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toUserId")
    private User toUser;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;


    @Builder
    public Validate(Template template, User fromUser, User toUser, Post post) {
        this.template = template;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.post = post;
    }
}
