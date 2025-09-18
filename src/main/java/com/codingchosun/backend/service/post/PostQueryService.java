package com.codingchosun.backend.service.post;

import com.codingchosun.backend.domain.Post;
import com.codingchosun.backend.domain.PostUser;
import com.codingchosun.backend.dto.response.MyPostResponse;
import com.codingchosun.backend.dto.response.UserDTO;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.notfoundfromdb.PostNotFoundFromDB;
import com.codingchosun.backend.repository.post.DataJpaPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryService {

    private final DataJpaPostRepository postRepository;

    public List<MyPostResponse> getMyPosts(String loginId) {
        List<Post> participatedPosts = postRepository.findParticipatedPosts(loginId);

        if (participatedPosts.isEmpty()) {
            throw new PostNotFoundFromDB(ErrorCode.POST_NOT_FOUND);
        }

        log.info("게시물 목록: {}", participatedPosts);

        return participatedPosts.stream()
                .map(MyPostResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getParticipants(Long postId) {
        Post post = findPostById(postId);

        return post.getPostUsers().stream().map(PostUser::getUser).map(UserDTO::from).toList();
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundFromDB(ErrorCode.POST_NOT_FOUND)
        );
    }
}
