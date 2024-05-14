package com.codingchosun.backend.response;


import com.codingchosun.backend.constants.StateCode;
import com.codingchosun.backend.domain.Comment;
import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostResponse {
    private Long postId;

    private String title;

    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private StateCode stateCode;

    private BigInteger viewCount;
    private UserDTO userDTO;
    private List<String> hashList;
    private List<CommentDTO> commentList;

    public PostResponse(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.startTime = post.getStartTime();
        this.endTime = post.getEndTime();
        this.stateCode = post.getStateCode();
        this.viewCount = post.getViewCount();
        this.userDTO = new UserDTO(post.getUser());

        this.hashList = post.getPostHashes().stream()
                .map(postHash -> postHash.getHashtag().getHashtagName()) // 해시태그 이름 추출
                .collect(Collectors.toList());

        this.commentList = post.getComments().stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }

    @Data
    public static class UserDTO{
        private Long userId;
        private String nickname;

        public UserDTO(User user){
            this.userId = user.getUserId();
            this.nickname = user.getNickname();
        }
    }

    @Data
    public static class CommentDTO{
        private Long commentId;
        private String content;
        private LocalDateTime createdAt;
        private UserDTO userDTO;

        public CommentDTO(Comment comment){
            this.commentId = comment.getCommentId();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
            this.userDTO = new UserDTO(comment.getUser());
        }
    }
}
