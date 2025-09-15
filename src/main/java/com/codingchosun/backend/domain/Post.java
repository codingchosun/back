package com.codingchosun.backend.domain;

import com.codingchosun.backend.constants.StateCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@Table
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private StateCode stateCode;

    private Long viewCount;

    @OneToMany(mappedBy = "post")
    private List<PostHash> postHashes;

    @OneToMany(mappedBy = "post")
    private List<PostUser> postUsers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<Image> images;

    @OneToMany(mappedBy = "post")
    private List<Validate> validates;

    public void increaseViewCount() {
        this.viewCount++;
    }
}