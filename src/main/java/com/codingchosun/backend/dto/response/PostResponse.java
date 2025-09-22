package com.codingchosun.backend.dto.response;

import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime startTime;
    private StateCode stateCode;
    private Long viewCount;

    private String loginId;
    private String nickname;

    private List<String> hashtags;
    private List<ImageResponse> images;
    private List<CommentResponse> comments;
    private List<UserDTO> participants;

    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.startTime = post.getStartTime();
        this.viewCount = post.getViewCount();
        this.loginId = post.getUser().getLoginId();
        this.nickname = post.getUser().getNickname();
        this.hashtags = post.getPostHashes().stream()
                .map(postHash -> postHash.getHashtag().getHashtagName())
                .collect(Collectors.toList());
        this.images = post.getImages().stream()
                .map(ImageResponse::from)
                .collect(Collectors.toList());

        this.comments = post.getComments().stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());

        this.participants = post.getPostUsers().stream()
                .map(postUser -> UserDTO.from(postUser.getUser()))
                .collect(Collectors.toList());
    }

    public static PostResponse from(Post post) {
        PostResponse response = new PostResponse();
        response.postId = post.getPostId();
        response.title = post.getTitle();
        response.content = post.getContent();
        response.createdAt = post.getCreatedAt();
        response.startTime = post.getStartTime();
        response.stateCode = post.getStateCode();
        response.viewCount = post.getViewCount();
        response.loginId = post.getUser().getLoginId();
        response.nickname = post.getUser().getNickname();
        response.hashtags = post.getPostHashes().stream()
                .map(postHash -> postHash.getHashtag().getHashtagName())
                .collect(Collectors.toList());
        response.images = post.getImages().stream()
                .map(ImageResponse::from)
                .collect(Collectors.toList());
        response.comments = post.getComments().stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
        response.participants = post.getPostUsers().stream()
                .map(postUser -> UserDTO.from(postUser.getUser()))
                .collect(Collectors.toList());
        return response;
    }
}
