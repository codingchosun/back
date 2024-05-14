package com.codingchosun.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter @Setter
@Table
public class Hashtag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    private String hashtagName;

    @OneToMany(mappedBy = "hashtag")
    private List<PostHash> hashtags;

    @OneToMany(mappedBy = "hashtag")
    private List<UserHash> userHashes;
}
