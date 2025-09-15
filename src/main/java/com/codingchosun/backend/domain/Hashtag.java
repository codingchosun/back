package com.codingchosun.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table
@NoArgsConstructor
public class Hashtag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    @NotNull
    @Column(unique = true)
    private String hashtagName;

    @OneToMany(mappedBy = "hashtag")
    private List<PostHash> hashtags;

    @OneToMany(mappedBy = "hashtag")
    private List<UserHash> userHashes;
}
