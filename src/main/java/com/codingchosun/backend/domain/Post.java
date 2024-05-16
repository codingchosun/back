package com.codingchosun.backend.domain;

import com.codingchosun.backend.constants.StateCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;
import java.util.List;

//@Check(constraints = "start_time >= created_at AND end_time >= created_at")
//예외 조건 추가할지 이야기 할것
//사유: 추가하면 귀찮아질듯

@Entity
@Getter @Setter
@Table
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private StateCode stateCode;

    private  Long viewCount;

    @OneToMany(mappedBy = "post")
    private List<PostHash> postHashes;

    @OneToMany(mappedBy = "post")
    private List<PostUser> postUsers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
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