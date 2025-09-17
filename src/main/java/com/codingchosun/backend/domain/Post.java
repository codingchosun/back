package com.codingchosun.backend.domain;

import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.exception.invalidrequest.InvalidEditorException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Setter
    private String title;

    @Setter
    private String content;

    @Setter
    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime startTime;

    @Setter
    private LocalDateTime endTime;

    @Setter
    @Enumerated(EnumType.STRING)
    private StateCode stateCode;

    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "post")
    private List<PostHash> postHashes;

    @OneToMany(mappedBy = "post")
    private List<PostUser> postUsers;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<Image> images;

    @OneToMany(mappedBy = "post")
    private List<Validate> validates;

    public Post(String title, String content, LocalDateTime startTime, User user) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.startTime = startTime;
        this.endTime = startTime.plusDays(1);
        this.stateCode = StateCode.ACTIVE;
        this.viewCount = 0L;
        this.user = user;
    }

    public void update(String title, String content, LocalDateTime startTime) {
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = startTime.plusDays(1);
    }

    public void deletePost() {
        this.stateCode = StateCode.INACTIVE;
    }

    public void validateOwner(User user) {
        if (!this.user.getUserId().equals(user.getUserId())) {
            throw new InvalidEditorException("게시물 수정 권한이 없습니다.");
        }
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void updateHashtags(List<Hashtag> hashtags) {
        this.postHashes.clear();
        for (Hashtag hashtag : hashtags) {
            this.postHashes.add(new PostHash(this, hashtag));
        }
    }
}