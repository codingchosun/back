package com.codingchosun.backend.dto.response;

import com.codingchosun.backend.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class LoginPostsResponse {

    private Long postId;
    private String title;
    private String nickname;
    private String url;
    private List<String> hashtags;
    private boolean recommend;

    public LoginPostsResponse(Long postId, String title, String nickname, String url, List<String> hashtags, boolean recommend) {
        this.postId = postId;
        this.title = title;
        this.nickname = nickname;
        this.url = url;
        this.hashtags = hashtags;
        this.recommend = recommend;
    }

    public static LoginPostsResponse from(Post post, boolean recommend) {
        return new LoginPostsResponse(
                post.getPostId(),
                post.getTitle(),
                post.getUser().getNickname(),
                !post.getImages().isEmpty() ? post.getImages().get(0).getUrl() : null,
                post.getPostHashes().stream().map(postHash -> postHash.getHashtag().getHashtagName()).collect(Collectors.toList()),
                recommend
        );
    }
}
