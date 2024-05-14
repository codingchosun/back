package com.codingchosun.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table
public class UserHash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtagId")
    private Hashtag hashtag;
}
