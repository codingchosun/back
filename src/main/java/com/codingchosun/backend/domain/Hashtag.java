package com.codingchosun.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    @NotNull
    @Setter
    @Column(unique = true)
    private String hashtagName;

    @OneToMany(mappedBy = "hashtag")
    private List<PostHash> hashtags;

    @OneToMany(mappedBy = "hashtag")
    private List<UserHash> userHashes;
}
